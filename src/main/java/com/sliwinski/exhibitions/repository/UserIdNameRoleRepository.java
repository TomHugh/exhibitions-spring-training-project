package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.UserIdNameRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserIdNameRoleRepository extends JpaRepository<UserIdNameRole, Long> {
}