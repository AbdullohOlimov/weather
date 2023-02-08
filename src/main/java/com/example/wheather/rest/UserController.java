package com.example.wheather.rest;

import com.example.wheather.dto.UserReqDto;
import com.example.wheather.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserReqDto reqDto){
        return userService.save(reqDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> findAllUsers(){
        return userService.findAllUsers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return userService.findByIdUsers(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody UserReqDto userReqDto){
        return userService.update(userReqDto);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PatchMapping("/subscribe")
    public ResponseEntity<?> update(@RequestParam Integer userId, @RequestParam Integer cityId){
        return userService.subscribe(userId, cityId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER') or hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/weather/{id}")
    public ResponseEntity<?> getWeather(@PathVariable Integer id){
        return userService.getWeather(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-city")
    public ResponseEntity<?> banUser(@RequestParam Integer userId, @RequestParam Integer cityId){
        return userService.banUser(userId, cityId);
    }
}
