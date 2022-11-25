package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.user.role.Feature;
import com.switchfully.eurder.domain.user.role.UsernamePassword;
import com.switchfully.eurder.service.exception.UnauthorizatedException;
import com.switchfully.eurder.service.exception.WrongCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class SecurityService {
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private final CustomerRepository customerRepository;

    public static final String SALT = "By-the-eurder-of-the-peaky-Stef";

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String getHashedPassword(String password) {
        String saltedPassword = SALT + password;
        return generateHash(saltedPassword);
    }

    public Boolean doesPasswordMatch(String username, String password) {
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        String storedPasswordHash = customerRepository.getCustomerByEmail(username).getPassword();
        return hashedPassword.equals(storedPasswordHash);
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm exists!");
        }

        return hash.toString();
    }

    public void validateAuthorization(String authorization, Feature feature) {
        if(authorization==null){
            throw new UnauthorizatedException();
        }

        UsernamePassword usernamePassword = getUsernamePassword(authorization);

        if (customerRepository.getCustomerByEmail(getEmail(authorization)) == null || !doesPasswordMatch(getEmail(authorization), usernamePassword.getPassword())) {
            logger.error("Email and/or password are incorrect. Please try again!");
            throw new WrongCredentialsException();
        }
            Customer customer = customerRepository.getCustomerByEmail(getEmail(authorization));

            if (!customer.canHaveAccessTo(feature)) {
                logger.error("User " + customer.getFirstName() + " " + customer.getLastName() + " does not have access to " + feature);
                throw new UnauthorizatedException();
            }
    }

    public String getEmail(String auth){
        return getUsernamePassword(auth).getUsername();
    }

    private UsernamePassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new UsernamePassword(username, password);
    }
}
