//package com.example.onlineshopproject.integration;
//
//import com.example.onlineshopproject.service.CategoryService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc(printOnlyOnFailure = false)
//@ActiveProfiles(profiles = {"dev"})
//public class CategoryIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private CategoryService categoryService;
//
//    @Test
//    void getAllTest() throws Exception {
//        Assertions.assertEquals(4, categoryService.getAll().size());
//    }
//
//    @Test
//    void getByIdTest() throws Exception {
//        ResultActions resultActions = this.mockMvc.perform(get("/categories/{categoryId}", 1)).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.categoryId").value(1));
/////Question
//        //Request processing failed: java.lang.NullPointerException: Cannot invoke "com.example.onlineshopproject.dto.CategoryResponseDto.getCategoryId()" because "categoryResponseDto" is null
//        //
//        //	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1022)
//        //	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903)
//    }
//}
