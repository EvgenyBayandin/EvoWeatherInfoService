package ru.webdev.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.weather.model.Main;
import ru.webdev.weather.model.Root;

@RestController
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    String appId;

    @Value("${url.weather}")
    String urlWeather;

    @GetMapping("/weather")
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {

        String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", urlWeather, lat, lon, appId);

        return restTemplate.getForObject(request, Root.class).getMain();
    }

}
