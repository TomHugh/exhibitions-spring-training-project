package com.sliwinski.exhibitions.config;

import com.sliwinski.exhibitions.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerHandler(Model model) {
        model.addAttribute("message", "wrong_message");
        return "error";
    }

    @ExceptionHandler(value = {CustomException.class})
    public String customHandler(HttpServletRequest req, RedirectAttributes redirectAttributes, CustomException e) {
        redirectAttributes.addFlashAttribute("class", "alert-danger");
        redirectAttributes.addFlashAttribute("message", e.getMessageKey());
        String referer = req.getHeader("Referer");
        return "redirect:" + referer;
    }
}
