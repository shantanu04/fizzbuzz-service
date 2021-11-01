package com.myapp.fizzbuzzservice.service;

import com.myapp.fizzbuzzservice.dto.ParameterDto;
import com.myapp.fizzbuzzservice.exception.FizzBuzzServiceException;
import com.myapp.fizzbuzzservice.persistence.ParameterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FizzBuzzServiceImplTest {

    @Mock
    private ParameterRepository parameterRepositoryMock;

    @InjectMocks
    private FizzBuzzServiceImpl service;

    @Test
    void shouldCreateFizzBuzzPattern() {
        // Given
        int param1 = 2;
        int param2 = 3;
        int limit = 10;
        String str1 = "fizz";
        String str2 = "buzz";

        // When
        String actual = service.createFizzBuzzPattern(param1, param2, limit, str1, str2);

        // Then
        assertEquals("1,fizz,buzz,fizz,5,fizzbuzz,7,fizz,buzz,fizz", actual.toLowerCase());
    }

    @Test
    void shouldValidateInputParameters() {
        // Given
        int param1 = 2;
        int param2 = 3;
        int limit = 10;

        // When - Then
        assertDoesNotThrow(() -> service.validateInputParameters(param1, param2, limit));
    }

    @Test
    void shouldThrowException() {
        // Given
        int param1 = 2;
        int param2 = 5;
        int limit = 4;

        // When
        FizzBuzzServiceException exception = assertThrows(FizzBuzzServiceException.class, () -> service.validateInputParameters(param1, param2, limit));

        // Then
        assertEquals("Param2 cannot be greater than limit.", exception.getMessage());
    }

    @Test
    void shouldGetParameterFromDb() {
        // Given
        String id = "2_7_20_fizz_buzz";
        ParameterDto mockParameterDto = mock(ParameterDto.class);

        when(parameterRepositoryMock.findById(id)).thenReturn(Optional.of(mockParameterDto));

        // When
        ParameterDto actual = service.getParameterFromDb(id);

        // Then
        assertEquals(mockParameterDto, actual);
    }

    @Test
    void shouldGetStatistics() {
        // Given
        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setParam1(2);
        parameterDto.setParam2(5);
        parameterDto.setLimit(10);
        parameterDto.setStr1("tikk");
        parameterDto.setStr2("tokk");
        parameterDto.setCount(3);
        List<ParameterDto> parameterDtoList = new ArrayList<>();
        parameterDtoList.add(parameterDto);

        when(parameterRepositoryMock.findAll(Sort.by(Sort.Direction.DESC, "count"))).thenReturn(parameterDtoList);

        // When - Then
        assertDoesNotThrow(() -> service.getStatistics());
    }
}