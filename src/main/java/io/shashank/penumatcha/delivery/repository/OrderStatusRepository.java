package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.OrderStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findOneByName(@Param(value="name") String name);
}
