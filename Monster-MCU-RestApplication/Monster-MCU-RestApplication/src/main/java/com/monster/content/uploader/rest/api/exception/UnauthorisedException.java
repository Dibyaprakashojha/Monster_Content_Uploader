package com.monster.content.uploader.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorisedException extends RuntimeException
{
    
    private static final long serialVersionUID = 1L;
    
    public UnauthorisedException(String msg)
    {
        super(msg);
    }
    
}