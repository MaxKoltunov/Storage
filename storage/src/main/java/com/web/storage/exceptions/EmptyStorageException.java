package com.web.storage.exceptions;

public class EmptyStorageException extends RuntimeException {

    public EmptyStorageException(String message) {
        super(message);
    }
}
