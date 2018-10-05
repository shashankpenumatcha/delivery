package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.OrderTracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderTracker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTrackerRepository extends JpaRepository<OrderTracker, Long> {

}
