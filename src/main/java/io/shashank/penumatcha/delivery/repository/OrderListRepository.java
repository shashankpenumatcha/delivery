package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.OrderList;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the OrderList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {

    @Query(value="select ol from OrderList ol join OrderStatus os on os.id = ol.orderStatus.id " +
        "join OrderItems oi on ol.id = oi.orderList.id " +
        "join UserProfile up on up.id = ol.userProfile.id " +
        "where os.id in (:orderStatuses) and ol.userProfile.id= :userProfileId " +
        "group by ol.id order by os.id asc")
    List<OrderList> getActiveOrdersForUser(@Param("userProfileId") Long userProfileId,
                                          @Param("orderStatuses") List<Long> orderStatuses);

}
