package com.test.elementiIngegneria.controller;

import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Custom error controller that handles error pages and responses.
 * Implements Spring's ErrorController interface to provide custom error handling.
 */
@Controller
public class CustomErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
    private final ErrorAttributes errorAttributes;

    /**
     * Constructs a new CustomErrorController with the specified error attributes.
     *
     * @param errorAttributes The ErrorAttributes instance to use for error handling
     */
    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Handles error requests and renders the error page.
     *
     * @param request The HTTP request that resulted in an error
     * @param model   The Spring MVC model for passing attributes to the view
     * @return The name of the error view template to render
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        var attrs = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.BINDING_ERRORS
        ));
        Integer status = (Integer) attrs.get("status");
        String message = (String) attrs.get("message");

        logger.error("Pagina d'errore {}: {}", status, message);

        model.addAttribute("errorMessage", message);
        model.addAttribute("statusCode", status);
        return "error";
    }
}