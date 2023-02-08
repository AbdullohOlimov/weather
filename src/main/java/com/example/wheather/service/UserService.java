package com.example.wheather.service;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.dto.UserReqDto;
import com.example.wheather.entity.Users;
import com.example.wheather.mapper.UserMapper;
import com.example.wheather.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public ResponseEntity<?> save(UserReqDto userReqDto){
        return ResponseEntity.ok(ResponseDto.getSuccess(userMapper.toDto(userRepo.save(userMapper.toEntity(userReqDto)))));
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
