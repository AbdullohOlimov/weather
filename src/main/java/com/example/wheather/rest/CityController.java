package com.example.wheather.rest;

import com.example.wheather.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;


    @GetMapping()
    public ResponseEntity<?> findAllCities(){
       return cityService.getAllCity();
    }
}
