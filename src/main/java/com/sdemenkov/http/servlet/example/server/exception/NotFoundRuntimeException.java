package com.sdemenkov.http.servlet.example.server.exception;

public class NotFoundRuntimeException extends RuntimeException {
    public NotFoundRuntimeException(Exception e) {
        super(e);
    }

    public NotFoundRuntimeException(String propertiesFileIsNotFound) {
        super(propertiesFileIsNotFound);
    }
}
