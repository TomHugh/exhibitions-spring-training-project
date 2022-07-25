package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
