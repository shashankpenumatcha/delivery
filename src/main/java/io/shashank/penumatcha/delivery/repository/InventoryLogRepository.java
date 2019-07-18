package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.InventoryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
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
public interface InventoryLogRepository extends PagingAndSortingRepository<InventoryLog, Long> {

    @Query(value="select il from InventoryLog il where il.date between :from and :to order by il.product.id")
    Page<InventoryLog> getReport(Pageable page, @Param(value = "from") ZonedDateTime from, @Param(value = "to") ZonedDateTime to);


}
