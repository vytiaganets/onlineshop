//package com.example.onlineshopproject.integration;
//
//import com.example.onlineshopproject.security.jwt.JwtRequest;
//import com.example.onlineshopproject.security.jwt.JwtRequestRefresh;
//import com.example.onlineshopproject.security.jwt.JwtResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc(printOnlyOnFailure = false)
//@ActiveProfiles(profiles = {"dev"})
//public class AuthIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void getNewAccessTokenAll() throws Exception {
//        JwtRequest authRequest = new JwtRequest();
//        authRequest.setLogin("andrii@ukr.net");
//        authRequest.setPassword("1234");
//        String requestBody = objectMapper.writeValueAsString(authRequest);
//        String content = this.
//                mockMvc
//                .perform(post("/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken")
//                        .exists())
//                .andExpect(jsonPath("$.refreshToken")
//                        .exists())
//                .andExpect(jsonPath("$.type").value("Bearer"))
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        JwtResponse responseTest = objectMapper.readValue(content, JwtResponse.class);
//
//        JwtRequestRefresh authRefresh = new JwtRequestRefresh();
//        authRefresh.setRefreshToken(responseTest.getRefreshToken());
//        requestBody = objectMapper.writeValueAsString(authRefresh);
//        this.mockMvc
//                .perform(post("/users/token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").exists())
//                .andExpect(jsonPath("$.refreshToken")
//                        .doesNotExist())
//                .andExpect(jsonPath("$.type").value("Bearer"));
//    }
//}
