package bg.moviebox.repository;


import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByRoles(UserRoleEnum role);
}
