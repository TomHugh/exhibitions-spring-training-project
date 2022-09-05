package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.exception.UserExistsException;
import com.sliwinski.exhibitions.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long getUserQuantity() {
        return userRepository.countByRole(Role.USER);
    }

    public Long getAdminQuantity() {
        return userRepository.countByRole(Role.ADMIN);
    }

    @Transactional
    public void createUser(String username, String password) {
        if(userRepository.countByUsername(username) == 0) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Role.USER);
            userRepository.save(user);
            log.info("User created: {}", user.getUsername());
        } else throw new UserExistsException();
    }

    @Transactional
    public void createAdmin(String username, String password) {
        if(userRepository.countByUsername(username) == 0) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            log.info("Admin created: {}", user.getUsername());
        } else throw new UserExistsException();
    }
}

