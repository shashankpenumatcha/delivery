package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.OrderList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {

}
