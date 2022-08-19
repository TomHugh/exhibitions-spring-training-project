package com.sliwinski.exhibitions.config;

import com.sliwinski.exhibitions.exception.CustomException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    //not handling BadCredentials exception from SecurityConfiguration  because its not controller exception
    @ExceptionHandler(BadCredentialsException.class)
    public String badCredentialsHandler(Model model) {
        model.addAttribute("class", "alert-danger");
        model.addAttribute("message", "bad_credentials");
        return "signin";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerHandler(Model model) {
        model.addAttribute("message", "wrong_message");
        return "error";
    }

    //not preserving model attributes during exception handling so when exception occurs during exhibition creation (details view) I loose all fields.
    // It doesn't matter for most views with small amount of fields but for details view, free locations which were delivered before have to be preserved.
    @ExceptionHandler(value = {CustomException.class})
    public String customHandler(HttpServletRequest req, RedirectAttributes redirectAttributes, CustomException e) {
        redirectAttributes.addFlashAttribute("class", "alert-danger");
        redirectAttributes.addFlashAttribute("message", e.getMessageKey());
        String referer = req.getHeader("Referer");
        return "redirect:" + referer;
    }
}
