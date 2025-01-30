package com.assessment.exception;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test Case 1: User not found exception
    @Test
    public void testHandleUserNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException("User not found");
        ResponseEntity<Map<String, Object>> responseEntity = globalExceptionHandler.handleUserNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Map<String, Object> responseBody = responseEntity.getBody();

        assertNotNull(responseBody);
        assertEquals("User Not Found", responseBody.get("error"));
        assertEquals("User not found", responseBody.get("message"));
    }

    // Test Case 2: Validation Exception
    @Test
    public void testHandleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "defaultMessage");
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        ResponseEntity<Map<String, Object>> responseEntity = globalExceptionHandler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Map<String, Object> responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("Invalid input", responseBody.get("error"));
        assertEquals("defaultMessage", responseBody.get("message"));
    }

    // Test Case 3: Generic Exception
    @Test
    public void testHandleGenericException() {
        Exception ex = new Exception("Unexpected error");
        ResponseEntity<Map<String, Object>> responseEntity = globalExceptionHandler.handleGenericException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Map<String, Object> responseBody = responseEntity.getBody();

        assertNotNull(responseBody);
        assertEquals("Internal server error", responseBody.get("error"));
        assertEquals("An unexpected error occurred. Please try again.", responseBody.get("message"));
    }
}