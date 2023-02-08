package com.example.wheather.repo;

import com.example.wheather.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Integer> {
    boolean existsByName(String name);
}
