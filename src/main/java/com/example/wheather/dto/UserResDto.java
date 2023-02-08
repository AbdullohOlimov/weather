package com.example.wheather.dto;

import com.example.wheather.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResDto {
    private Integer id;
    private String username;
    private String password;
    private Set<City> cities;
}
