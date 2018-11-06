package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.FcmToken;
import io.shashank.penumatcha.delivery.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@SuppressWarnings("unused")
@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    @Query(value="select ft.token from FcmToken ft where ft.userProfile.id = :userProfileId")
    List<FcmToken> findAllForUser(@Param("userProfileId") Long userProfileId);

    @Query(value="select ft from FcmToken ft where ft.token = :token")
    FcmToken findOneByToken(@Param("token")String token);
}
