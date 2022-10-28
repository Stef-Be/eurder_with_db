package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.user.Feature;
import com.switchfully.eurder.domain.user.UsernamePassword;
import com.switchfully.eurder.service.exception.UnauthorizatedException;
import com.switchfully.eurder.service.exception.WrongPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private final CustomerRepository customerRepository;

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateAuthorization(String authorization, Feature feature) {
        if(authorization==null){
            throw new UnauthorizatedException();
        }
        UsernamePassword usernamePassword = getUsernamePassword(authorization);
        Customer customer = customerRepository.getCustomerbyEmail(getEmail(authorization));

        if(!customer.doesPasswordMatch(usernamePassword.getPassword())) {
            logger.error("Password does not match for user " + usernamePassword.getUsername());
            throw new WrongPasswordException();
        }
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
