package com.example.onlineshopproject.controller.advice;

import com.example.onlineshopproject.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@RestControllerAdvice
public class AdviceController {

    //AV Handling an invalid cart argument
    @ExceptionHandler(CartInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartInvalidArgumentException cartInvalidArgumentException) {
        log.error("Invalid cart argument: {}", cartInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Invalid cart argument."));
    }

    //AV Handling invalid cart item argument
    @ExceptionHandler(CartItemInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartItemInvalidArgumentException cartItemInvalidArgumentException) {
        log.error("Invalid cart item argument: {}", cartItemInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Invalid cart item argument."));
    }

    //AV Handling the error of an item not found in the cart
    @ExceptionHandler(CartItemNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartItemNotFoundException cartItemNotFoundException) {
        log.error("Product not found in cart: {}", cartItemNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Product not found in cart."));
    }

    //AV Error handling cart not found
    @ExceptionHandler(CartNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartNotFoundException cartNotFoundException) {
        log.error("Cart not found: {}", cartNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Cart not found."));
    }

    //AV Handling an invalid category argument error
    @ExceptionHandler(CategoryInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(CategoryInvalidArgumentException categoryInvalidArgumentException) {
        log.error("Invalid category argument: {}", categoryInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Invalid category argument."));
    }

    //AV Error handling category not found
    @ExceptionHandler(CategoryNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(CategoryNotFoundException categoryNotFoundException) {
        log.error("Category not found: {}", categoryNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Category not found."));
    }

    //AV Error handling incorrect category value
    @ExceptionHandler(CategoryWrongValueException.class)
    public ResponseEntity<ErrorMessage> handleException(CategoryWrongValueException categoryWrongValueException) {
        log.error("Invalid category value: {}", categoryWrongValueException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Invalid category value."));
    }

    //AV Error handling: incorrect parameter value
    @ExceptionHandler(ErrorParamException.class)
    public final ResponseEntity<ErrorMessage> handleException(ErrorParamException errorParamException) {
        log.error("Invalid parameter value: {}", errorParamException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Invalid parameter value."));
    }

    //AV Error handling not found favorites
    @ExceptionHandler(FavoriteNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(FavoriteNotFoundException favoriteNotFoundException) {
        log.error("Favorites not found: {}", favoriteNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Favorites not found."));
    }

    //AV Handling Invalid Data Access API Usage Exception

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public final ResponseEntity<ErrorMessage> handleException(InvalidDataAccessApiUsageException invalidDataAccessApiUsageException) {
        log.error("Invalid Data Access API usage exception: {}",invalidDataAccessApiUsageException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Invalid Data Access API Usage Exception."));
    }
    //AV Handling 404 database errors
    @ExceptionHandler(NotFoundInDbException.class)
    public final ResponseEntity<ErrorMessage> handleException(NotFoundInDbException notFoundInDbException) {
        log.error("Not found in database: {}", notFoundInDbException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(notFoundInDbException.getMessage()));
    }

    //AV Error handling order not found
    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(OrderNotFoundException orderNotFoundException) {
        log.error("Order not found: {}", orderNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Order not found."));
    }

    //AV Error handling incorrect product value
    @ExceptionHandler(ProductIllegalArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(ProductIllegalArgumentException productIllegalArgumentException) {
        log.error("Incorrect product value: {}", productIllegalArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Incorrect product value."));
    }

    //AV Error handling product not found
    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(ProductNotFoundException productNotFoundException) {
        log.error("Product not found: {}", productNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Product not found."));
    }

    //AV Handling 400 errors
    @ExceptionHandler(ResponseException.class)
    public final ResponseEntity<ErrorMessage> handleException(ResponseException responseException) {
        log.error("Exception: {}", responseException.getMessage());
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(new ErrorMessage("Sorry, something went wrong. Please try again later"));
    }

    //AV Error handling invalid user value
    @ExceptionHandler(UserInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(UserInvalidArgumentException userInvalidArgumentException) {
        log.error("Invalid user value: {}", userInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Invalid user value."));
    }

    //AV Error handling user not found
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(UserNotFoundException userInvalidArgumentException) {
        log.error("User is not found: {}", userInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("User is not found."));
    }

    //AV Handling validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        log.error("The argument is invalid: {}", methodArgumentNotValidException.getMessage());
        return new ResponseEntity<>(getErrorMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //AV getErrorMap method
    private Map<String, List<String>> getErrorMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    //AV Handling other exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleException(Exception exception) {
        log.error("Exception: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(new ErrorMessage("Sorry, something went wrong. Please try again later"));
    }
}
