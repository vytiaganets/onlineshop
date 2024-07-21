package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.ChatRequest;
import com.example.onlineshopproject.model.BookModel;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenAIServiceImpl implements GenAIService {
    private final Assistant assistant;
    private final RAGAssistant ragAssistant;
    @Override
    public String getResponse(ChatRequest request) {
        return assistant.chat(request.userId(), request.question());
    }

    @Override
    public String getResponseExtended(ChatRequest request) {
        return ragAssistant.chat(request.userId(), request.question());
    }

    @Override
    public BookModel getBookModelFromText(String question) {
        var popularGenres = List.of("Fiction", "Mystery", "Romance", "Fantasy", "Thriller");
        return assistant.extractBookFromText(question, popularGenres);
    }


    public String getResponseSimple(ChatRequest request) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        chatMessageList.add(SystemMessage.systemMessage("Respond in Russian"));
        chatMessageList.add(UserMessage.userMessage(request.question()));
        var model = OpenAiChatModel.builder()
                .apiKey("demo")
                .build();
        return model.generate(chatMessageList).content().text();
    }
}
