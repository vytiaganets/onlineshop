package com.example.onlineshopproject.integration;

import com.example.onlineshopproject.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles(profiles = {"dev"})
public class ProductIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    void getAllTest() throws Exception {
        Assertions.assertEquals(1, productServiceImpl.getAll().size());
//        this.mockMvc.perform(get("/products")).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$..productId").exists());
//2024-07-08 16:37:58.933 | main                      | ERROR | com.example.onlineshopproject.controller.advice.AdviceController        -       Exception: org.hibernate.query.ParameterLabelException: Gap between '?2' and '?5' in ordinal parameter labels (ordinal parameters must be labelled sequentially)
    }

    @Test
    void getByIdTest() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/products/{productId}", 1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1));
    }

}
