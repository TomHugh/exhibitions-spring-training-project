package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.entity.UserIdNameRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserIdNameRoleRepository extends JpaRepository<UserIdNameRole, Long> {
}