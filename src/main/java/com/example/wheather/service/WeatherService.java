package com.example.wheather.service;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.entity.City;
import com.example.wheather.entity.Weather;
import com.example.wheather.repo.CityRepo;
import com.example.wheather.repo.WeatherRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepo weatherRepo;
    private final CityRepo cityRepo;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> updateAllCity(){
        List<City> cities = cityRepo.findAll();
        List<Weather> weathers = new LinkedList<>();
        for (City city: cities){
            Optional<Weather> optionalWeather = weatherRepo.findByCityId(city.getId());
            Weather weather = getData(city.getName());
            optionalWeather.ifPresent(value -> weather.setId(value.getId()));
            weather.setCity(city);
            weathers.add(weather);
        }

        return ResponseEntity.ok(weatherRepo.saveAll(weathers));
    }


    public ResponseEntity<?> updateDataById(Integer cityId) {
        Optional<City> optionalCity = cityRepo.findById(cityId);
        if (optionalCity.isPresent()){
            City city = optionalCity.get();
            Weather weather = getData(city.getName());
            weather.setCity(city);
            Optional<Weather> optionalWeather = weatherRepo.findByCityId(cityId);
            optionalWeather.ifPresent(value -> weather.setId(value.getId()));
            return ResponseEntity.ok(weatherRepo.save(weather));
        }else{
            return ResponseEntity.badRequest().body(ResponseDto.getError("not updated", "city not found"));
        }


    }

    private Weather getData(String city) {
        String json = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=" + city + ",uz&appid=2300d429630058871993058db910a620", String.class);
        Gson gson = new Gson();
        JsonObject weather = gson.fromJson(json, JsonObject.class);
        Weather weather1 = new Weather();
        weather1.setTemperature(Math.round((Double.parseDouble(weather.getAsJsonObject("main").get("temp").getAsString()) - 273.15) * 100.0) / 100.0);
        weather1.setFeelsLike(Math.round((Double.parseDouble(weather.getAsJsonObject("main").get("feels_like").getAsString()) - 273.15) * 100.0) / 100.0);
        weather1.setWindSpeed(Double.parseDouble(weather.getAsJsonObject("wind").get("speed").getAsString()));
        weather1.setDescription(weather.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString());
        return weather1;
    }


}
