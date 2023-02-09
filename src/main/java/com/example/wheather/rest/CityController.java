package com.example.wheather.rest;

import com.example.wheather.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;


    @PreAuthorize("hasRole('ROLE_ADMIN')  or hasAnyRole('ROLE_USER')")
    @GetMapping()
    public ResponseEntity<?> findAllCities(){
       return cityService.getAllCity();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestParam String name, @RequestParam Integer id){
        return cityService.update(id, name);
    }
}
