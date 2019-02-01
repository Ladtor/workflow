package com.ladtor.workflow.exception;

/**
 * @author liudongrong
 * @date 2019/1/30 19:18
 */
public class HttpClientException extends RuntimeException {
    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
