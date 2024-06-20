package ru.webdev.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import ru.webdev.weather.model.Main;
import ru.webdev.weather.model.Root;

@RestController
public class WeatherController {

    @Value("${appid}")
    String appId;

    @Value("${url.weather}")
    String urlWeather;

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CacheManager cacheManager;


    @GetMapping("/weather")
    @Cacheable(value = "weather-cache", key = "#lat+'_'+#lon")
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {

        // Проверим наличие даных в кеше по ключу
        String key = lat+"_"+lon;
        Main cachedData = cacheManager.getCache("weather-cache").get(key, Main.class);
        if(cachedData != null){
            // Получим данные из кеша
            return cachedData;
        } else {
            // Если нет, то получим данные из API и сохраним их в кеш
            String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", urlWeather, lat, lon, appId);
            Main mainData  = restTemplate.getForObject(request, Root.class).getMain();
            cacheManager.getCache("weather-cache").put(key, mainData);
            return mainData;
        }

    }

}
