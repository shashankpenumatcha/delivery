package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.active = true")
    List<Product> getActiveProducts();

}
