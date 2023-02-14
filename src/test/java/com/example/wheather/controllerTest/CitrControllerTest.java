package com.example.wheather.controllerTest;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class CitrControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final Gson gson = new Gson();

    @Test
    @SneakyThrows
    @WithMockUser(authorities = {"ROLE_ADMIN", "ROLE_USER"})
    public void getCitiesTest() {
        mockMvc.perform(get("http://localhost:8080/city"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getById() {
        mockMvc.perform(put("http://localhost:8080/city?id=1&name=Tashkent")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok")
                ).andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getByIdNameError() {
        mockMvc.perform(put("http://localhost:8080/city?id=1&name=Namangan")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("not updated")
                ).andExpect(jsonPath("$.message").value("city exists"))
        ;


    }
}