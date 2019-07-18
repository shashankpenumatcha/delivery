package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.active = true and p.quantity >= p.minimumQuantity")
    List<Product> getActiveProducts();

    @Query("select p from Product p where p.active = true and p.quantity >= p.minimumQuantity and p.vendor.id=:vendorId")
    List<Product> getActiveProductsByVendor(@Param(value="vendorId") Long VendorId);
}
