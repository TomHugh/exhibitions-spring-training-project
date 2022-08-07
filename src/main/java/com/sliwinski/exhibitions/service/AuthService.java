package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthService {

    public User getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public String getUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }

    public void addUsernameAttribute(Model model){
        String username = getUsername();
        if(!username.equals("anonymousUser")) model.addAttribute("username", username);
    }
}
