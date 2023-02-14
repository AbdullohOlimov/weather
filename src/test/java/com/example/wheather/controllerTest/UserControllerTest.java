package com.example.wheather.integration;

import com.example.wheather.dto.UserReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final Gson gson = new Gson();

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getAll() {
        mockMvc.perform(get("/user/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getById() {
        mockMvc.perform(get("/user/id/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.username").isString());
    }

    @Test
    @SneakyThrows
    public void save() {
        UserReqDto reqDto = new UserReqDto();
        reqDto.setUsername("test123");
        reqDto.setPassword("test123");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        reqDto.setCityIds(list);
        mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(reqDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }


    @Test
    @SneakyThrows
    public void saveError() {
        UserReqDto reqDto = new UserReqDto();
        reqDto.setUsername("test");
        reqDto.setPassword("test123");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        reqDto.setCityIds(list);
        mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(reqDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("not saved"))
                .andExpect(jsonPath("$.message").value("username is already in use"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void updateTest() {
        UserReqDto reqDto = new UserReqDto();
        reqDto.setId(2);
        reqDto.setUsername("test");
        reqDto.setPassword("test123");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        reqDto.setCityIds(list);
        mockMvc.perform(put("/user/update").
                contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(reqDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void updateError() {
        UserReqDto reqDto = new UserReqDto();
        reqDto.setId(3);
        reqDto.setUsername("test123");
        reqDto.setPassword("test123");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        reqDto.setCityIds(list);
        mockMvc.perform(put("/user/update").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(reqDto))
                ).andDo(print()).andExpect(status().isOk()
                ).andExpect(jsonPath("$.status").value("not updated"))
                .andExpect(jsonPath("$.message").value("username is already in use"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void updateErrorAgain() {
        UserReqDto reqDto = new UserReqDto();
        reqDto.setId(15);
        reqDto.setUsername("test123");
        reqDto.setPassword("test123");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        reqDto.setCityIds(list);
        mockMvc.perform(put("/user/update").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(reqDto))
                ).andDo(print()).andExpect(status().isOk()
                ).andExpect(jsonPath("$.status").value("not updated"))
                .andExpect(jsonPath("$.message").value("user not found, id invalid"));

    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void subscribeTest(){
        mockMvc.perform(patch("/user/subscribe?userId=4&cityId=5")
        ).andDo(print()).andExpect(status().isOk()
        ).andExpect(jsonPath("$.status").value("ok")
        ).andExpect(jsonPath("$.message").value("success")
        ).andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void subscribeError(){
        mockMvc.perform(patch("/user/subscribe?userId=78&cityId=78")
        ).andDo(print()).andExpect(status().isOk()
        ).andExpect(jsonPath("$.status").value("not added")
        ).andExpect(jsonPath("$.message").value("user or city not found"));

    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getWeatherTest(){
        mockMvc.perform(get("http://localhost:8080/user/weather/4")
        ).andDo(print()).andExpect(status().isOk()
        ).andExpect(jsonPath("$.status").value("ok")
        ).andExpect(jsonPath("$.message").value("success")
        ).andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getWeatherErrorTest(){
        mockMvc.perform(get("http://localhost:8080/user/weather/48")
        ).andDo(print()).andExpect(status().isOk()
        ).andExpect(jsonPath("$.status").value("not found")
        ).andExpect(jsonPath("$.message").value("user not found biy id")
        );
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void banUser(){
        mockMvc.perform(delete("http://localhost:8080/user/delete-city?userId=3&cityId=3")
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void banUserError(){
        mockMvc.perform(delete("http://localhost:8080/user/delete-city?userId=3&cityId=3")
                ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("not added"))
                .andExpect(jsonPath("$.message").value("user or city not found"));
    }




}