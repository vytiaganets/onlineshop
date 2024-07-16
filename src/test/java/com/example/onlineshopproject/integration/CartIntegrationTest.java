//package com.example.onlineshopproject.integration;
//
//import com.example.onlineshopproject.service.CartService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc(printOnlyOnFailure = false)
//@ActiveProfiles(profiles = {"dev"})
//public class CartIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private CartService cartService;
//
//    @Test
//    void getByIdTest() throws Exception {
//        mockMvc.perform(get("/cart/{userId}", 1)).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(1));
//        ///Question
//        //Handler dispatch failed: java.lang.AssertionError
//        //
//        //	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1104)
//        //	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979)
//        //	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)
//        //	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903)
//        //	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
//        //	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)
//        //	at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
//        //	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //	at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:165)
//    }
//
//}
