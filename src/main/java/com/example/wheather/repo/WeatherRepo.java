package com.example.wheather.repo;

import com.example.wheather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.desktop.OpenFilesEvent;
import java.util.Optional;

public interface WeatherRepo extends JpaRepository<Weather, Integer> {
    Optional<Weather> findByCityId(Integer id);
}
