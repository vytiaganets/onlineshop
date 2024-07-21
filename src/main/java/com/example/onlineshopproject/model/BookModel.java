package com.example.onlineshopproject.model;

import jdk.jfr.Description;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookModel {
    private Integer id;
    private String title;
    @Description("Should be less than 20 words")
    private String description;
    private String genre;
    private LocalDate dateOfPublication;
    private String author;
}
