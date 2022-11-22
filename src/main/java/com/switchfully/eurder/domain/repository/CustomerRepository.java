package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer getCustomerByEmail(String email);
}
