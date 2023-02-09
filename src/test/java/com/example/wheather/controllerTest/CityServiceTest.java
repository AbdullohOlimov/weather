package com.example.wheather;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.entity.City;
import com.example.wheather.repo.CityRepo;
import com.example.wheather.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

    @ExtendWith(MockitoExtension.class)
public class CityServiceTest {
    @Mock
    private CityRepo cityRepo;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testGetAllCity() {
        List<City> cities = Arrays.asList(new City(1, "New York"), new City(2, "London"));
        when(cityRepo.findAll()).thenReturn(cities);
        ResponseEntity<?> response = cityService.getAllCity();
        ResponseDto body = (ResponseDto) response.getBody();
        assert body != null;
        Assertions.assertEquals(response.getStatusCodeValue(), 200);
        Assertions.assertEquals(body.getData(), cities);        ;
    }

    @Test
    public void testUpdateSuccess() {
        City city = new City(1, "New York");
        when(cityRepo.findById(1)).thenReturn(Optional.of(city));
        when(cityRepo.existsByName("London")).thenReturn(false);
        when(cityRepo.save(city)).thenReturn(new City(1, "London"));

        ResponseEntity<?> response = cityService.update(1, "London");

        ResponseDto body = (ResponseDto) response.getBody();
        assert body != null;

        Assertions.assertEquals(response.getStatusCodeValue(), 200);

        Assertions.assertEquals(body.getData(), new City(1, "London"));
        ;
    }

    @Test
    public void testUpdateCityExists() {
        City city = new City(1, "New York");
        when(cityRepo.findById(1)).thenReturn(Optional.of(city));
        when(cityRepo.existsByName("London")).thenReturn(true);

        ResponseEntity<?> response = cityService.update(1, "London");

        ResponseDto body = (ResponseDto) response.getBody();
        assert body != null;

        Assertions.assertEquals(response.getStatusCodeValue(), 200);
        Assertions.assertEquals(body.getStatus(), "not updated");
        Assertions.assertEquals(body.getMessage(), "city exists");
    }

    @Test
    public void testUpdateCityNotFound() {
        when(cityRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = cityService.update(1, "London");
        ResponseDto body = (ResponseDto) response.getBody();
        assert body != null;
        Assertions.assertEquals(response.getStatusCodeValue(), 200);
        Assertions.assertEquals(body.getStatus(), "not updated");
        Assertions.assertEquals(body.getMessage(), "city not found");
    }
}