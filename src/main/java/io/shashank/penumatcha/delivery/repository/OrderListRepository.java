package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.OrderList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the OrderList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderListRepository extends PagingAndSortingRepository<OrderList, Long> {

    List<OrderList> findAll();

    @Query(value="select ol from OrderList ol join OrderStatus os on os.id = ol.orderStatus.id " +
        "join OrderItems oi on ol.id = oi.orderList.id " +
        "join UserProfile up on up.id = ol.userProfile.id " +
        "where os.id in (:orderStatuses) and ol.userProfile.id= :userProfileId " +
        "group by ol.id")
    Page<OrderList> getActiveOrPastOrdersForUser(Pageable pageRequest,@Param("userProfileId") Long userProfileId,
                                           @Param("orderStatuses") List<Long> orderStatuses);

    @Query(value="select count(ol.id) from OrderList ol join OrderStatus os on os.id = ol.orderStatus.id" +
        " where os.id in (:orderStatuses)")
    Long getActiveOrdersCount(@Param("orderStatuses") List<Long> orderStatuses);


    @Query("select ol from OrderList ol join ol.orderStatus os on os.id = ol.orderStatus.id " +
        " where os.id = :type")
    Page<OrderList> getOrdersByType(Pageable pageRequest, @Param("type") Long type);



    @Query(value="select count(ol.id) from OrderList ol join OrderStatus os on os.id = ol.orderStatus.id" +
        " where os.name = :orderStatus")
    Long getOrdersCount(@Param("orderStatus") String orderStatus );

}
