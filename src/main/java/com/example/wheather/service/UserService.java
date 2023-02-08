package com.example.wheather.service;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.dto.UserReqDto;
import com.example.wheather.dto.UserResDto;
import com.example.wheather.entity.City;
import com.example.wheather.entity.Users;
import com.example.wheather.entity.Weather;
import com.example.wheather.mapper.UserMapper;
import com.example.wheather.repo.CityRepo;
import com.example.wheather.repo.UserRepo;
import com.example.wheather.repo.WeatherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final CityRepo cityRepo;
    private final WeatherRepo weatherRepo;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> save(UserReqDto userReqDto){
        Users users = userMapper.toEntity(userReqDto);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return ResponseEntity.ok(ResponseDto.getSuccess(userMapper.toDto(userRepo.save(users))));
    }

    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(ResponseDto.getSuccess(userRepo.findAll().stream().map(userMapper::toDto).collect(Collectors.toList())));
    }

    public ResponseEntity<?> findByIdUsers(Integer userId){
        Optional<Users> optionalUsers = userRepo.findById(userId);
        if (optionalUsers.isPresent()){
            return ResponseEntity.ok(ResponseDto.getSuccess(userMapper.toDto(optionalUsers.get())));
        }else{
            return ResponseEntity.ok(ResponseDto.getError("not found", "user not found"));
        }
    }

    public ResponseEntity<?> update(UserReqDto userReqDto){
        Users users = userMapper.toEntity(userReqDto);
        Optional<Users> optionalUsers = userRepo.findById(users.getId());
        if (optionalUsers.isPresent()){
            Users user = optionalUsers.get();
            if (users.getUsername().equals(user.getUsername()) || !userRepo.existsByUsername(users.getUsername())){
                if (users.getCities() == null) users.setCities(user.getCities());
                else{
                    Set<City> cities = users.getCities();
                    cities.addAll(user.getCities());
                }
                users.setPassword(passwordEncoder.encode(users.getPassword()));
                return ResponseEntity.ok(ResponseDto.getSuccess(userMapper.toDto(userRepo.save(users))));
            }else{
                return ResponseEntity.ok(ResponseDto.getError("not updated", "username is already exists"));
            }
        }else{
            return ResponseEntity.ok(ResponseDto.getError("not updated", "user not found, id invalid"));
        }


    }

    public ResponseEntity<?> subscribe(Integer userId, Integer cityId){
        Optional<Users> optionalUsers = userRepo.findById(userId);
        Optional<City> optionalCity = cityRepo.findById(cityId);
        if (optionalCity.isPresent() && optionalUsers.isPresent()){
            Users users = optionalUsers.get();
            City city = optionalCity.get();
            users.getCities().add(city);
            users.setCities(users.getCities());
            return ResponseEntity.ok(ResponseDto.getSuccess(userMapper.toDto(userRepo.save(users))));
        }else{
            return ResponseEntity.ok(ResponseDto.getError("not added", "user or city not found"));
        }
    }

    public ResponseEntity<?> getWeather(Integer userId){
        Optional<Users> optionalUsers = userRepo.findById(userId);
        if (optionalUsers.isPresent()){
            Users users = optionalUsers.get();
            Set<City> cities = users.getCities();
            Set<Weather> collect = cities.stream().map(e -> weatherRepo.findByCityName(e.getName())).collect(Collectors.toSet());
            return ResponseEntity.ok(ResponseDto.getSuccess(collect));
        }else{
            return ResponseEntity.ok(ResponseDto.getError("not found", "user not found biy id"));
        }
    }

    public ResponseEntity<?> banUser(Integer userId, Integer cityId){
        Optional<Users> optionalUsers = userRepo.findById(userId);
        Optional<City> optionalCity = cityRepo.findById(cityId);
        if (optionalUsers.isPresent() && optionalCity.isPresent()){
            Users user = optionalUsers.get();
            City city = optionalCity.get();
            Set<City> cities = user.getCities();
            cities.remove(city);
            user.setCities(cities);
            return ResponseEntity.ok(ResponseDto.getSuccess(userMapper.toDto(userRepo.save(user))));
        }else{
            return ResponseEntity.ok(ResponseDto.getError("not added", "user or city not found"));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userRepo.findByUsername(username);
        if (optionalUsers.isPresent()) {
            return optionalUsers.get();
        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
