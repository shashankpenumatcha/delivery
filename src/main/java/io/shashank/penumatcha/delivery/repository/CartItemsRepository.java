package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.CartItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CartItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

}
