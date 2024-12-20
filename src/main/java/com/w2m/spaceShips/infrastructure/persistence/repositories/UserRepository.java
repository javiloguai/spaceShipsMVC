package com.w2m.spaceShips.infrastructure.persistence.repositories;

import com.w2m.spaceShips.infrastructure.persistence.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author javiloguai
 */
@Repository
public interface UserRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByUsername(String username);

}
