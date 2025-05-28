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

@Controller
public class CustomErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Recupera attributi di errore (status, message, stacktrace se necessario)
        ServletWebRequest webRequest = new ServletWebRequest(request);
        var attrs = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.BINDING_ERRORS
        ));
        Integer status = (Integer) attrs.get("status");
        String message = (String) attrs.get("message");

        // Log più lirico
        logger.error("Pagina d'errore {}: {}", status, message);

        model.addAttribute("errorMessage", message);
        model.addAttribute("statusCode", status);
        return "error"; // Thymeleaf template error.html
    }
}