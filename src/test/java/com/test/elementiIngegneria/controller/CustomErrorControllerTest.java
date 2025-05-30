package com.test.elementiIngegneria.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(CustomErrorController.class)
class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ErrorAttributes errorAttributes;

    @Test
    void handleError_ShouldReturnErrorView_WhenErrorOccurs() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        Model model = mock(Model.class);
        ServletWebRequest webRequest = new ServletWebRequest(request);

        Map<String, Object> errorAttributesMap = new HashMap<>();
        errorAttributesMap.put("status", 404);
        errorAttributesMap.put("message", "Not Found");

        when(errorAttributes.getErrorAttributes(eq(webRequest), any(ErrorAttributeOptions.class)))
                .thenReturn(errorAttributesMap);

        CustomErrorController controller = new CustomErrorController(errorAttributes);

        // Act
        String viewName = controller.handleError(request, model);

        // Assert
        verify(model).addAttribute("errorMessage", "Not Found");
        verify(model).addAttribute("statusCode", 404);
        assertThat(viewName).isEqualTo("error");
    }

    @Test
    void handleError_ShouldHandleNullMessageAndStatus() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        Model model = mock(Model.class);
        ServletWebRequest webRequest = new ServletWebRequest(request);

        Map<String, Object> errorAttributesMap = new HashMap<>();
        errorAttributesMap.put("status", null);
        errorAttributesMap.put("message", null);

        when(errorAttributes.getErrorAttributes(eq(webRequest), any(ErrorAttributeOptions.class)))
                .thenReturn(errorAttributesMap);

        CustomErrorController controller = new CustomErrorController(errorAttributes);

        // Act
        String viewName = controller.handleError(request, model);

        // Assert
        verify(model).addAttribute("errorMessage", (String) null);
        verify(model).addAttribute("statusCode", null);
        assertThat(viewName).isEqualTo("error");
    }

    @Test
    void handleError_ShouldLogErrorDetails() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        Model model = mock(Model.class);
        ServletWebRequest webRequest = new ServletWebRequest(request);

        Map<String, Object> errorAttributesMap = new HashMap<>();
        errorAttributesMap.put("status", 500);
        errorAttributesMap.put("message", "Internal Server Error");

        when(errorAttributes.getErrorAttributes(eq(webRequest), any(ErrorAttributeOptions.class)))
                .thenReturn(errorAttributesMap);

        CustomErrorController controller = spy(new CustomErrorController(errorAttributes));

        // Act
        String viewName = controller.handleError(request, model);

        // Assert
        verify(model).addAttribute("errorMessage", "Internal Server Error");
        verify(model).addAttribute("statusCode", 500);
        verify(controller).handleError(request, model);
        assertThat(viewName).isEqualTo("error");
    }
}