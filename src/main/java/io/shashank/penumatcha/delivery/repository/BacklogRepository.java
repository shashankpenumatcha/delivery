package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.Backlog;
import io.shashank.penumatcha.delivery.domain.InventoryLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;


/**
 * Spring Data  repository for the InventoryLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    @Query(value="select sum(il.quantity) from Backlog b join b.inventoryLog il on il.id = b.inventoryLog.id join " +
        "il.product p on p.id = il.product.id where p.id = :productId ")
    Float getByProductId(@Param(value="productId") Long ProductId);

}
