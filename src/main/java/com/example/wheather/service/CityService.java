package com.example.wheather.service;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.entity.City;
import com.example.wheather.repo.CityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepo cityRepo;

    public ResponseEntity<?> getAllCity() {
        return ResponseEntity.ok(ResponseDto.getSuccess(cityRepo.findAll()));
    }

    public ResponseEntity<?> update(Integer id, String name) {
        Optional<City> optional = cityRepo.findById(id);
        if (optional.isPresent()) {
            if (!cityRepo.existsByName(name)) {
                City city = optional.get();
                city.setName(name);
                return ResponseEntity.ok(ResponseDto.getSuccess(cityRepo.save(city)));
            } else {
                return ResponseEntity.ok(ResponseDto.getError("not updated", "city exists"));
            }
        } else {
            return ResponseEntity.ok(ResponseDto.getError("not updated", "city not found"));
        }
    }
}
