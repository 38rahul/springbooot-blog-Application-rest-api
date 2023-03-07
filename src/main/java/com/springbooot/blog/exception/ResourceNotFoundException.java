package com.springbooot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName,fieldValue)); // post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }

}
// The DTO stands for data transfer object and it is a frequently used design pattern to pass a data with
//multiple parameters from client to server in a one shot.
//So basically, to avoid remote calls or server calls
//we can also pass a DTO from server to client.

//    In many of the, you know, developers, prefer either one approach, but in typical, you know, big applications

//        I have seen that we basically use a DTO object to transfer data between client and server

 //       and this is the best approach.

 //       The advantage of using DTO's on REST API in Java is that they can help to hide implementation details
//       of JPA entities.

//      So exposing JPA entities via REST endpoints can become a security issue
