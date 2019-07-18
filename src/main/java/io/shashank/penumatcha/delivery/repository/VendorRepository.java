package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.Backlog;
import io.shashank.penumatcha.delivery.domain.InventoryLog;
import io.shashank.penumatcha.delivery.domain.Vendor;
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
public interface VendorRepository extends JpaRepository<Vendor, Long> {


}
