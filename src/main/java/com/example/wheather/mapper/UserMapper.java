package com.example.wheather.mapper;

import com.example.wheather.dto.UserReqDto;
import com.example.wheather.dto.UserResDto;
import com.example.wheather.entity.Users;
import com.example.wheather.repo.CityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final CityRepo cityRepo;

    public Users toEntity(UserReqDto userReqDto) {
        return new Users(userReqDto.getId(), userReqDto.getUsername(), userReqDto.getPassword(), new HashSet<>(cityRepo.findAllById(userReqDto.getCityIds())), "USER", true);
    }

    public UserResDto toDto(Users user){
        return new UserResDto(user.getId(), user.getUsername(), user.getCities());
    }
}
