package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.UserAddress;
import io.shashank.penumatcha.delivery.domain.UserProfile;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the UserProfile entity.
 */

@SuppressWarnings("unused")
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    @Query("select ua from UserAddress ua where ua.userProfile.id = :userProfile")
    List<UserAddress> findAllByUserProfile(@Param("userProfile") Long userProfile);
}
