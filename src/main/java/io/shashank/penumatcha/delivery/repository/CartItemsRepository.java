package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.Cart;
import io.shashank.penumatcha.delivery.domain.CartItems;
import io.shashank.penumatcha.delivery.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CartItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    @Query("select ci from CartItems ci where ci.product.id=:productId")
    CartItems getProductInCart(@Param(value="productId") Long productId);

    @Query("select c from Cart c join FETCH c.cartItems ci where c.userProfile.id=:id")
    Cart getCartItemsForUser(@Param(value="id") Long id);



}
