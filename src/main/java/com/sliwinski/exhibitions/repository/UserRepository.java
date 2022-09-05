package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
     long countByRole(Role role);


     Optional<User> findByUsername(String username);

     long countByUsername(String username);
}