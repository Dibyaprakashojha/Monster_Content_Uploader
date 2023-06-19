package com.monster.content.uploader.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public ServiceRuntimeException(String msg)
    {
        super(msg);
    }
}