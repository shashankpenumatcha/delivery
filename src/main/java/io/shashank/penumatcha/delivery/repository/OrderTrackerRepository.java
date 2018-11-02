package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.OrderTracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the OrderTracker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTrackerRepository extends JpaRepository<OrderTracker, Long> {

    @Query(value="select ot from OrderTracker ot join ot.orderStatus os on os.id = ot.orderStatus.id " +
        "join ot.orderList ol on ol.id = ot.orderList.id where ol.id = :id order by ot.dateTime asc")
    List<OrderTracker> trackOrder(@Param("id") Long id);
}
