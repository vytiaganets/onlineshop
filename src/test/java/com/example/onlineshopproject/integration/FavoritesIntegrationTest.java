//package com.example.onlineshopproject.integration;
//import com.example.onlineshopproject.service.FavoriteService;
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
//public class FavoritesIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private FavoriteService favoriteService;
//
//
//    @Test
//    void getByUserIdTest() throws Exception {
//        mockMvc.perform(get("/favorites/{userId}", 1)).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$..favoriteId").value(1));
//    }
//}
