package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i.price from Item i where i.id = :itemId")
    double getItemPrice(@Param("itemId") long itemId);

}
