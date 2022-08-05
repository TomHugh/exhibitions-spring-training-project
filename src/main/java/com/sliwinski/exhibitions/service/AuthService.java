package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import com.sliwinski.exhibitions.repository.OrderRepository;
import com.sliwinski.exhibitions.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    public String getAuthenticatedUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }
}
