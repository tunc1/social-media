package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.User;

public interface UserRepository extends JpaRepository<User,Long>
{
    boolean existsByUsername(String username);
    User findByUsername(String username);
    boolean existsByEmail(String email);
}