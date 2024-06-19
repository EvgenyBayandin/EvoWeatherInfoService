package ru.webdev.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.weather.model.Root;

@RestController
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    String appid;

    @GetMapping("/weather")
    public Root getWeather() {
        return restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?lat=54.1838&lon=45.1749&units=metric&appid=" + appid, Root.class);
    }

}
