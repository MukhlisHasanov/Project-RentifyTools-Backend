package org.rentifytools.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message) {
        super("User with email " + message + " already exists");
    }
}
