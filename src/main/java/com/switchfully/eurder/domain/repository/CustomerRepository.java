package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer getCustomerByEmail(String email);
}
