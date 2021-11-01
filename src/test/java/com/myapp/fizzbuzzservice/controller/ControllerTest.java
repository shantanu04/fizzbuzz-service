package com.myapp.fizzbuzzservice.controller;

import com.myapp.fizzbuzzservice.exception.FizzBuzzServiceException;
import com.myapp.fizzbuzzservice.service.FizzBuzzService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @Mock
    private FizzBuzzService fizzBuzzServiceMock;

    @InjectMocks
    private Controller controller;

    @Test
    void shouldCreateFizzBuzzPattern() {
        // Given
        int param1 = 2;
        int param2 = 3;
        int limit = 50;
        String str1 = "Fizz";
        String str2 = "Buzz";

        when(fizzBuzzServiceMock.createFizzBuzzPattern(param1, param2, limit, str1, str2))
                .thenReturn("resultingString");

        // When
        ResponseEntity<String> actual = controller.getFizzBuzz(param1, param2, limit, str1, str2);

        // Then
        assertEquals("resultingString", actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void shouldThrowFizzBuzzServiceException() {
        // Given
        int param1 = 2;
        int param2 = 5;
        int limit = 4;
        String str1 = "Fizz";
        String str2 = "Buzz";

        when(fizzBuzzServiceMock.createFizzBuzzPattern(param1, param2, limit, str1, str2))
                .thenThrow(FizzBuzzServiceException.class);

        // When
        ResponseEntity<String> actual = controller.getFizzBuzz(param1, param2, limit, str1, str2);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void shouldGetStatistics() {
        // Given
        when(fizzBuzzServiceMock.getStatistics()).thenReturn("statisticsString");

        // When
        ResponseEntity<String> actual = controller.getStatistics();

        // Then
        assertEquals("statisticsString", actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void shouldThrowNotFoundException() {
        // Given
        when(fizzBuzzServiceMock.getStatistics()).thenThrow(IllegalStateException.class);

        // When
        ResponseEntity<String> actual = controller.getStatistics();

        // Then
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }
}