package com.example.wheather.service;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.repo.CityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepo cityRepo;

    public ResponseEntity<?> getAllCity(){
        return ResponseEntity.ok(ResponseDto.getSuccess(cityRepo.findAll()));
    }
}
