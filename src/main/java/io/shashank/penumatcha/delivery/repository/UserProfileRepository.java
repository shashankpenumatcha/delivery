package io.shashank.penumatcha.delivery.repository;

import io.shashank.penumatcha.delivery.domain.UserProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query("select up from UserProfile up join up.user u on u.id=up.user.id where u.login =:login")
    UserProfile findUserProfileByLogin(@Param(value="login") String login);

}
