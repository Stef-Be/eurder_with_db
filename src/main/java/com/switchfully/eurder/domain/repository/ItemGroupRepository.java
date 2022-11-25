package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.order.ItemGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemGroupRepository extends JpaRepository<ItemGroup, Long> {
    @Query("select i from ItemGroup i where i.order.id = :orderId")
    List<ItemGroup> findAllByOrderId(@Param("orderId") long orderId);
}
