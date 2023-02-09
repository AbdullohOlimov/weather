package com.example.wheather.controllerTest;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.dto.UserReqDto;
import com.example.wheather.entity.Users;
import com.example.wheather.mapper.UserMapper;
import com.example.wheather.repo.UserRepo;
import com.example.wheather.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
/*    @Mock
    UserRepo userRepo;
    @Mock
    UserReqDto userReqDto;
    @Mock
    UserService userService;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceTest userServiceTest;


    @Test
    public void saveTest() {
        UserReqDto userReqDto1 = new UserReqDto();
        userReqDto1.setId(1);
        userReqDto1.setUsername("testuser");
        userReqDto1.setPassword("password");
        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        userReqDto1.setCityIds(l);
        when(userRepo.existsByUsername("testuser")).thenReturn(false);
        ResponseEntity<?> response = userService.save(userReqDto1);
        System.out.println(userReqDto1);
        ResponseDto body = (ResponseDto) response.getBody();
        assert body!= null;

        Assertions.assertEquals(response.getStatusCodeValue(), 200);
        Assertions.assertEquals(body.getStatus(), "ok");
    }


    @Test
    public void getAllUsersTest(){
        Set<Integer>  s = new HashSet<>();
        s.add(1);
        List<Users> list = Arrays.asList(new Users());
    }*/
}
