package com.example.springrest.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ProblemDetail handleNotFoundException(ResourceNotFoundException ex){
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());

        problemDetails.setTitle("Resource Not Found");
        problemDetails.setDetail("Resource Not Found");
        problemDetails.setProperty("Id", ex.getId());
        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetail handleInvalidCoordinatesException(InvalidCoordinatesException ex){
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        problemDetails.setTitle("Invalid coordinates");
        problemDetails.setDetail("Invalid coordinates. Latitude or longitude does not exist");
        problemDetails.setProperty("Lon", ex.getLon());
        problemDetails.setProperty("Lat", ex.getLat());
        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ProblemDetail handleAuthorizationException(AuthorizationException ex){
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getLocalizedMessage());

        problemDetails.setTitle("Not authorized");
        problemDetails.setDetail("Not authorized, please log in");
        problemDetails.setProperty("Username", ex.getUsername());
        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ProblemDetail handleResourceAlreadyExistException(ResourceAlreadyExistException ex){
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getLocalizedMessage());

        problemDetails.setTitle("Resource already exist");
        problemDetails.setDetail("Resource already exist, duplicates are not allowed");
        problemDetails.setProperty("Id", ex.getId());
        return problemDetails;
    }

}
