//package com.example.onlineshopproject.integration;
//
//import com.example.onlineshopproject.security.service.AuthService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc(printOnlyOnFailure = false)
//@ActiveProfiles(profiles = {"dev"})
//public class AuthIntegrationTest {
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//    @Autowired
//    private AuthService authService;
//    @Test
//    void login(){
//
//    }
//    @Test
//    void getAccessToken() throws Exception{
//        ResultActions resultActions = this
//                .mockMvc
//                .perform(post("/users/token", 1))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//}
