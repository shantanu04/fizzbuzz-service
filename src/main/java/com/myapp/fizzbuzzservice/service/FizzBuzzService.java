package com.myapp.fizzbuzzservice.service;

import com.myapp.fizzbuzzservice.exception.FizzBuzzServiceException;

public interface FizzBuzzService {

    /**
     * Method to create a Fizz-Buzz pattern string.
     *
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param limit  the limit parameter that will decide the length of the string
     * @param str1   the string used to replace multiples of @param1
     * @param str2   the string used to replace multiples of @param2
     * @return the resulting string following a pattern using the parameters provided
     * @throws FizzBuzzServiceException
     */
    String createFizzBuzzPattern(int param1, int param2, int limit, String str1, String str2) throws FizzBuzzServiceException;

    /**
     * Method to get statistics related to the most frequent request.
     *
     * @return the details related to the most frequent request
     */
    String getStatistics();

}
