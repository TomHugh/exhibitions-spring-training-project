package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.entity.UserIdNameRole;
import com.sliwinski.exhibitions.repository.UserIdNameRoleRepository;
import com.sliwinski.exhibitions.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserIdNameRoleService {
    private final int PAGE_SIZE = 10;

    private final UserIdNameRoleRepository userIdNameRoleRepository;

    public Page<UserIdNameRole> getAllUsers(int page) {
        return userIdNameRoleRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }
}

