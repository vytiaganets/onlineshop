package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ChatRequest;
import com.example.onlineshopproject.dto.ChatResponse;
import com.example.onlineshopproject.model.BookModel;
import com.example.onlineshopproject.service.GenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class GenerativeController {
    private final GenAIService genAIService;

    @PostMapping
    public ChatResponse getChatResponse(@RequestBody ChatRequest request) {
        return new ChatResponse(genAIService.getResponse(request));
    }

    @PostMapping("/extended")
    public ChatResponse getChatResponseExtended(@RequestBody ChatRequest request) {
        return new ChatResponse(genAIService.getResponseExtended(request));
    }

    @PostMapping("/book")
    public BookModel getBookModelFromText(@RequestBody ChatRequest request) {
        return genAIService.getBookModelFromText(request.question());
    }
}
