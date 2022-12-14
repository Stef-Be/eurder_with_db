package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllByCustomerId(long customerId);
}
