package net.nicolasdot.meal_service.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.nicolasdot.meal_service.entity.ExceptionMessage;
import net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.MealNotPossibleException;
import net.nicolasdot.meal_service.exceptions.NotPossibleException;
import net.nicolasdot.meal_service.exceptions.ReportingNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author nicolasdotnet
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleEntityNoFoudException(HttpServletRequest request, EntityNotFoundException e) {

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionMessage> handleEntityAlreadyExistsException(HttpServletRequest request, EntityAlreadyExistsException e) {

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotPossibleException.class)
    public ResponseEntity<ExceptionMessage> handleBookingNotPossibleException(HttpServletRequest request, NotPossibleException e) {

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MealNotPossibleException.class)
    public ResponseEntity<ExceptionMessage> handleBookingNotPossibleException(HttpServletRequest request, MealNotPossibleException e) {

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<String> getMessage = new ArrayList<>();

        for (FieldError error : errors) {
            getMessage.add("@" + error.getField().toUpperCase() + ":" + error.getDefaultMessage());
        }

        String text = String.format(getMessage.toString());

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(text)
                .build();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReportingNotPossibleException.class)
    public ResponseEntity<ExceptionMessage> handleBookingNotPossibleException(HttpServletRequest request, ReportingNotPossibleException e) {

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleException(HttpServletRequest request, Exception e) {

        ExceptionMessage message = ExceptionMessage.builder()
                .date(LocalDateTime.now().format(formatter))
                .path(request.getRequestURI() + "?" + request.getQueryString())
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
