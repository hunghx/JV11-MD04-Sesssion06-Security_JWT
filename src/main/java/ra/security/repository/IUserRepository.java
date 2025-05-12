package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.security.model.entity.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("From User where username = :user or email=:user or phone=:user")
    Optional<User> findByUsernameOrEmailOrPhone(@Param("user") String user);
}
