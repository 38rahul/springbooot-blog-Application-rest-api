package com.springbooot.blog.exception;

import com.springbooot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle Specfic Exceptions
    @ExceptionHandler(ResourceNotFoundException.class) // we use this annotation to handle specific Exception
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
                        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                                webRequest.getDescription(false));
            return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class) // we use this annotation to handle specific Exception
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Handle Global Exception

    @ExceptionHandler(Exception.class) // we use this annotation to handle specific Exception
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                               WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //  Approcah 1
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
           String fieldName = ((FieldError)error).getField();
           String message = error.getDefaultMessage();
           errors.put(fieldName,message);
        });
        return  new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    // Appproach 2
//    @ExceptionHandler(MethodArgumentNotValidException.class) // we use this annotation to handle specific Exception
//    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
//                                                                        WebRequest webRequest){
//        Map<String,String> errors = new HashMap<>();
//        exception.getBindingResult().getAllErrors().forEach((error)->{
//            String fieldName = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName,message);
//        });
//        return  new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(AccessDeniedException.class) // we use this annotation to handle specific Exception
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}



// What is Hash Code and equals are related
// Generationtion of Garbage Collections
// Design Patterns you know
// Factory pattern design Pattern
// Implement Singleton pattern
// Lamdba Expression
// Where this hash code is used ??
// What happens when hash funs returning the same hash code for all the Objects ??
// Did  you work on UML Diagram ??

//  java Script
// Do you know what is closures ?
//

// Why Joins in Sql ?
// Difference Between Left Outer and Right Outer Join ?
// What are DDL and DML Commands ?
// What is Oops concept ?
// What is Multi Threading ? Have you worked on that ?
// Coding Question: Split the String "online java Compiler" into Word


