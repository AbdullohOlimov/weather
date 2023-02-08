package com.example.wheather.rest;

import com.example.wheather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @PostMapping("/by-id")
    public ResponseEntity<?> updateData(@RequestParam Integer cityId) {
        return weatherService.updateDataById(cityId);
    }

    @PostMapping("/all")
    public ResponseEntity<?> updateData() {
        return weatherService.updateAllCity();
    }
}
