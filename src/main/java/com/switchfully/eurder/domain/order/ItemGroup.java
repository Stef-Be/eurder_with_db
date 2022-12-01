package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ITEM_GROUP")
public class ItemGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_group_seq")
    @SequenceGenerator(name = "item_group_seq", sequenceName = "item_group_seq", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "fk_item_id")
    private Item item;
    @Column(name="amount")
    private int amount;
    @Column(name="shipping_date")
    private LocalDate shippingDate;

    @ManyToOne
    @JoinColumn(name = "fk_order_id")
    private Order order;

    public ItemGroup(){}

    public ItemGroup(Item item, int amount, LocalDate shippingDate, Order order) {
        this.item = item;
        this.amount = amount;
        this.shippingDate = shippingDate;
        this.order = order;
    }

    //calculate shippingdate in het domein zetten en aanroepen in de constructor (Domain-Driven-Design, Eric Evans)
    //(Patterns, Principles, and Practices of Domain-Driven Design)


    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public Order getOrder() {
        return order;
    }

    public double calculateFinalPrice(){
        return getItem().getPrice()*amount;
    }
}
