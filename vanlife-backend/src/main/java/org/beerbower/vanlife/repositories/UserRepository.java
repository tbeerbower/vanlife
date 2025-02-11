package org.beerbower.vanlife.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.beerbower.vanlife.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
