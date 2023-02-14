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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final Gson gson = new Gson();

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void updateTestById() {
        mockMvc.perform(post("http://localhost:8080/weather/by-id?cityId=1")).andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void updateTestByIdError() {
        mockMvc.perform(post("http://localhost:8080/weather/by-id?cityId=15")).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void updateAllTest(){
        mockMvc.perform(post("http://localhost:8080/weather/all")).andDo(print())
                .andExpect(status().isOk());
    }

}