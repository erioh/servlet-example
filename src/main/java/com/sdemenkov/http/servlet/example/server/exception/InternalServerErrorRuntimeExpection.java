package com.sdemenkov.http.servlet.example.server.exception;

public class InternalServerErrorRuntimeExpection extends RuntimeException {
    public InternalServerErrorRuntimeExpection(String message) {
        super(message);
    }

    public InternalServerErrorRuntimeExpection(Throwable cause) {
        super(cause);
    }
}
