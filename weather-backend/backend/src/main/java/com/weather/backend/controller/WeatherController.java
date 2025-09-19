package com.weather.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Value("${openweathermap.api.key:6974ef968fe2221f575e6a2ef4204889}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // Concurrent map to track city usage counts
    private final ConcurrentHashMap<String, AtomicInteger> cityUsageMap = new ConcurrentHashMap<>();

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        // Increment city usage count
        cityUsageMap.compute(city.toLowerCase(), (key, count) -> {
            if (count == null) {
                return new AtomicInteger(1);
            } else {
                count.incrementAndGet();
                return count;
            }
        });

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch weather data"));
        }
    }

    // Getter for city usage map for other controllers to access
    public Map<String, AtomicInteger> getCityUsageMap() {
        return cityUsageMap;
    }
}
