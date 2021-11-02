package com.myapp.fizzbuzzservice.exception;

/**
 * @author shantanuk
 * <p>
 * Exception class for handling service exceptions
 */
public class FizzBuzzServiceException extends RuntimeException {

    public FizzBuzzServiceException(String message) {
        super(message);
    }

}
