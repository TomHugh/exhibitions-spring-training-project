package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthService {

    public User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void addUsernameAttribute(Model model){
        String username = getUsername();
        if(!username.equals("anonymousUser")) model.addAttribute("username", username);
    }
}
