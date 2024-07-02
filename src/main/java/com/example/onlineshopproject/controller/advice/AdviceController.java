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

    //AV Обработка недопустимого аргумента корзины
    @ExceptionHandler(CartInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartInvalidArgumentException cartInvalidArgumentException) {
        log.error("Недопустимый аргумент корзины: {}", cartInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Недопустимый аргумент корзины."));
    }

    //AV Обработка недопустимый аргумент товара корзины
    @ExceptionHandler(CartItemInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartItemInvalidArgumentException cartItemInvalidArgumentException) {
        log.error("Недопустимый аргумент товара корзины: {}", cartItemInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Недопустимый аргумент товара корзины."));
    }

    //AV Обработка ошибки ненайденого товара в корзине
    @ExceptionHandler(CartItemNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartItemNotFoundException cartItemNotFoundException) {
        log.error("Ненайден товар в корзине: {}", cartItemNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Ненайден товар в корзине."));
    }

    //AV Обработка ошибки не найдена корзина
    @ExceptionHandler(CartNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(CartNotFoundException cartNotFoundException) {
        log.error("Корзина не найдена: {}", cartNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Корзина не найдена."));
    }

    //AV Обработка ошибки недопустимого аргумента категории
    @ExceptionHandler(CategoryInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(CategoryInvalidArgumentException categoryInvalidArgumentException) {
        log.error("Недопустимый аргумент категории: {}", categoryInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Недопустимый аргумент категории."));
    }

    //AV Обработка ошибки не найдена категории
    @ExceptionHandler(CategoryNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(CategoryNotFoundException categoryNotFoundException) {
        log.error("Категория не найдена: {}", categoryNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Категория не найдена."));
    }

    //AV Обработка ошибки неправильное значение категории
    @ExceptionHandler(CategoryWrongValueException.class)
    public ResponseEntity<ErrorMessage> handleException(CategoryWrongValueException categoryWrongValueException) {
        log.error("Неправильное значение категории: {}", categoryWrongValueException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Неправильное значение категории."));
    }

    //AV Обработка ошибки неправильное значение параметра
    @ExceptionHandler(ErrorParamException.class)
    public final ResponseEntity<ErrorMessage> handleException(ErrorParamException errorParamException) {
        log.error("Неправильное значение параметра: {}", errorParamException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Неправильное значение параметра."));
    }

    //AV Обработка ошибки не найдена избранное
    @ExceptionHandler(FavoriteNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(FavoriteNotFoundException favoriteNotFoundException) {
        log.error("Избранное не найдено: {}", favoriteNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Избранное не найдено."));
    }

    //AV Обработка ошибок базы данных 404
    @ExceptionHandler(NotFoundInDbException.class)
    public final ResponseEntity<ErrorMessage> handleException(NotFoundInDbException notFoundInDbException) {
        log.error("Не найдено в базе данных: {}", notFoundInDbException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(notFoundInDbException.getMessage()));
    }

    //AV Обработка ошибки не найден заказ
    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(OrderNotFoundException orderNotFoundException) {
        log.error("Заказ не найден: {}", orderNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Заказ не найден."));
    }

    //AV Обработка ошибки неправильное значение продукта
    @ExceptionHandler(ProductIllegalArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(ProductIllegalArgumentException productIllegalArgumentException) {
        log.error("Неправильное значение продукта: {}", productIllegalArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Неправильное значение продукта."));
    }

    //AV Обработка ошибки не найден продукт
    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(ProductNotFoundException productNotFoundException) {
        log.error("Продукт не найден: {}", productNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Продукт не найден."));
    }

    //AV Обработка ошибок 400
    @ExceptionHandler(ResponseException.class)
    public final ResponseEntity<ErrorMessage> handleException(ResponseException responseException) {
        log.error("Исключение: {}", responseException.getMessage());
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(new ErrorMessage("Извините, что-то пошло не так. Повторите попытку позже"));
    }

    //AV Обработка ошибки неправильное значение пользователя
    @ExceptionHandler(UserInvalidArgumentException.class)
    public final ResponseEntity<ErrorMessage> handleException(UserInvalidArgumentException userInvalidArgumentException) {
        log.error("Неправильное значение пользователя: {}", userInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Неправильное значение пользователя."));
    }

    //AV Обработка ошибки не найден пользователь
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(UserNotFoundException userInvalidArgumentException) {
        log.error("Пользователь не найден: {}", userInvalidArgumentException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Пользователь не найден."));
    }

    //AV Обработка ошибок validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        log.error("Аргумент недействительный: {}", methodArgumentNotValidException.getMessage());
        return new ResponseEntity<>(getErrorMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //AV getErrorMap метод
    private Map<String, List<String>> getErrorMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    //AV Обработка остальных исключений
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleException(Exception exception) {
        log.error("Исключение: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(new ErrorMessage("Извините, что-то пошло не так. Повторите попытку позже"));
    }
}
