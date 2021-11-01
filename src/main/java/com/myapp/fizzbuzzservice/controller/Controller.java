package com.myapp.fizzbuzzservice.controller;

import com.myapp.fizzbuzzservice.exception.FizzBuzzServiceException;
import com.myapp.fizzbuzzservice.service.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private FizzBuzzService service;

    /**
     * Controller method to generate a Fizz-Buzz pattern string.
     *
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param limit  the limit parameter that will decide the length of the string
     * @param str1   the string used to replace multiples of @param1
     * @param str2   the string used to replace multiples of @param2
     * @return the result following a pattern using the parameters provided
     */
    @GetMapping(value = "/fizzbuzz", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getFizzBuzz(
            @RequestParam int param1,
            @RequestParam int param2,
            @RequestParam int limit,
            @RequestParam String str1,
            @RequestParam String str2
    ) {
        try {
            return ResponseEntity.ok(service.createFizzBuzzPattern(param1, param2, limit, str1, str2));
        } catch (FizzBuzzServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    /**
     * Controller method to get statistics related to the most frequent request.
     *
     * @return the details related to the most frequent request
     */
    @GetMapping(value = "/fizzbuzz/statistics", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStatistics() {
        try {
            return ResponseEntity.ok(service.getStatistics());
        } catch (IllegalStateException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
