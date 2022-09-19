package com.sliwinski.exhibitions.config;

import com.sliwinski.exhibitions.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Exceptions handling.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public String customHandler(HttpServletRequest req, RedirectAttributes redirectAttributes, CustomException e) {
        redirectAttributes.addFlashAttribute("class", "alert-danger");
        redirectAttributes.addFlashAttribute("message", e.getMessageKey());
        String referer = req.getHeader("Referer");
        return "redirect:" + referer;
    }
}
