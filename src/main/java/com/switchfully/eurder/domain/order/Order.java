package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.user.Customer;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name="fk_customer_id")
    private Customer customer;

    @Column(name="final_price")
    private double finalPrice;

    public Order(){};


    public Order(Customer customer, double finalPrice) {
        this.finalPrice = finalPrice;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }
}
