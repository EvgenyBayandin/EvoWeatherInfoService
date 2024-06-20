package ru.webdev.location.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.webdev.location.model.Location;
import ru.webdev.location.model.Weather;
import ru.webdev.location.repository.LocationRepository;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String location){
        Location geodata = repository.findByName(location).get();
        String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s", geodata.getLatitude(), geodata.getLongitude());
        return restTemplate.getForObject(url, Weather.class);
    }

    @GetMapping
    public Iterable<Location> findAll() {
        return repository.findAll();
    }

    @GetMapping("/")
    public Optional<Location> getLocation(@RequestParam String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping("/")
    public Location putLocation(@RequestBody Location location, @RequestParam String name)  {
        Location existing = repository.findByName(name).get();
        existing.setName(location.getName());
        existing.setLatitude(location.getLatitude());
        existing.setLongitude(location.getLongitude());
        return repository.save(existing);
    }

    @DeleteMapping("/")
    public void deleteLocation(@RequestParam String name)   {
        Location existing = repository.findByName(name).get();
        repository.delete(existing);
    }

}
