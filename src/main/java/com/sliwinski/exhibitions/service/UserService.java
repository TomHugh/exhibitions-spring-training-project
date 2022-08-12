package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final int PAGE_SIZE = 10;

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private int totalPages;
    public int getTotalPages() {
        return totalPages;
    }


    private Long userQuantity;
    public Long getUserQuantity() {
        if(userQuantity == null) userQuantity = userRepository.countByRole(Role.USER);
        return userQuantity;
    }

    private Long adminQuantity;
    public Long getAdminQuantity() {
        if(adminQuantity == null) adminQuantity = userRepository.countByRole(Role.ADMIN);
        return adminQuantity;
    }


    public Page<User> getAllUsers(int page) {
        Page<User> users = userRepository.findAllPageable(PageRequest.of(page, PAGE_SIZE));
        totalPages = users.getTotalPages();
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    public void createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.USER);
        userRepository.save(user);
        userQuantity++;
    }

    public void createAdmin(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}

