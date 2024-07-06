package com.example.onlineshopproject.integration;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles(profiles = {"dev"})
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Test
    void getAllUsersTest() throws Exception{
        //Assertions.assertEquals(2,userService.getUser().size());//сервис репо
        //RestAssure почитать
        this.mockMvc.perform(get("/v1/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists());
    }
    @Test
    void getUserByIdTest() throws Exception{
        ResultActions resultActions = this.mockMvc.perform(get("/v1/users/{id}", 1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }
    @Test
    void updateClientTests() throws Exception{
        UserRequestDto expectedUser = UserRequestDto.builder()
                .userId(1L)
                .email("andrii@ukr.net")
                .role(UserRole.ADMIN)
                .name("Test")
                .phoneNumber("123456789012")
                .passwordHash("1234")
                .build();
        String requestBody = objectMapper.writeValueAsString(expectedUser);
        this.mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }
}
