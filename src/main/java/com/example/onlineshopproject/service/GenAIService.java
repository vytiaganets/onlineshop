package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.ChatRequest;
import com.example.onlineshopproject.model.BookModel;
import org.apache.el.stream.StreamELResolverImpl;

public interface GenAIService {
    String getResponse(ChatRequest request);
    String getResponseExtended(ChatRequest request);
    BookModel getBookModelFromText(String question);
}
