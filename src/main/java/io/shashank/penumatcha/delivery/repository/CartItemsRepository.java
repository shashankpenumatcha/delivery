package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.Cart;
import io.shashank.penumatcha.delivery.domain.CartItems;
import io.shashank.penumatcha.delivery.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the CartItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    @Query("select ci from CartItems ci join ci.cart c on c.id = ci.cart.id " +
        "join c.userProfile up on up.id = c.userProfile.id where ci.product.id=:productId" +
        " and up.id = :userProfileId")
    CartItems getProductInCart(@Param(value="productId") Long productId, @Param(value="userProfileId") Long userProfileId);

    @Query("select c from Cart c join c.cartItems ci where c.userProfile.id=:id")
    Cart getCartForUser(@Param(value="id") Long id);

    @Query("select ci from CartItems ci join ci.cart c on ci.cart.id = c.id where c.userProfile.id=:id")
    Set<CartItems> getCartItemsForUser(@Param(value="id") Long id);

    @Query("select ci from CartItems ci join ci.cart c on ci.cart.id = c.id where c.id=:id")
    Set<CartItems> getCartItemsByCart(@Param(value="id") Long id);



}
