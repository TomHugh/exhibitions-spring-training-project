package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.exception.UserExistsException;
import com.sliwinski.exhibitions.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final int PAGE_SIZE = 10;

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserQuantity() {
        return userRepository.countByRole(Role.USER);
    }

    public Long getAdminQuantity() {
        return userRepository.countByRole(Role.ADMIN);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void createUser(String username, String password) {
        if(userRepository.countByUsername(username) == 0) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(Role.USER);
            userRepository.save(user);
        } else throw new UserExistsException();
    }

    public void createAdmin(String username, String password) {
        if(userRepository.countByUsername(username) == 0) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        } else throw new UserExistsException();
    }
}

