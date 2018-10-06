package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.Cart;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.userProfile.id = :userProfileId")
    Cart findByLogin(@Param(value="userProfileId") Long userProfileId);
}
