package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.InventoryLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InventoryLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, Long> {

}
