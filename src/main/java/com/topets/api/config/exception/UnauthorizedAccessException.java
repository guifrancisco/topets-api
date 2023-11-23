package com.topets.api.config.exception;

/**
 * The user did not have authorization to access a resource
 */
public class UnauthorizedAccessException extends Exception{
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
