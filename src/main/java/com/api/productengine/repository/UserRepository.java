package com.api.productengine.repository;

import com.api.productengine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByUsername(String username);
}
