package ru.webdev.location.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Weather> redirectRequestWeather(@RequestParam String name) {
        if (repository.findByName(name).isPresent()) {
            Location geodata = repository.findByName(name).get();
            String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s", geodata.getLatitude(), geodata.getLongitude());
            Weather weather = restTemplate.getForObject(url, Weather.class);
            return new ResponseEntity(weather, HttpStatus.OK);
        }
        return new ResponseEntity("Location by name: " + name + " not found.", HttpStatus.NOT_FOUND);
    }


    @GetMapping
    public ResponseEntity<List<Location>> findAll() {
        return repository.findAll().iterator().hasNext()
                ? new ResponseEntity(repository.findAll(), HttpStatus.OK)
                : new ResponseEntity("No locations found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Optional<Location>> getLocation(@RequestParam String name) {
        return repository.findByName(name).isPresent()
                ? new ResponseEntity(repository.findByName(name), HttpStatus.OK)
                : new ResponseEntity("Location by name: " + name + " not found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        return repository.findByName(location.getName()).isPresent()
                ? new ResponseEntity(repository.findByName(location.getName()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(location), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Location> putLocation(@RequestBody Location location, @RequestParam String name) {
        if (repository.findByName(name).isPresent()) {
            Location existing = repository.findByName(name).get();
            existing.setName(location.getName());
            existing.setLatitude(location.getLatitude());
            existing.setLongitude(location.getLongitude());
            repository.save(existing);
            return new ResponseEntity(existing, HttpStatus.OK);
        }
        return new ResponseEntity("Location by name: " + name + " not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<Location> deleteLocation(@RequestParam String name) {
        if (repository.findByName(name).isPresent()) {
            Location existing = repository.findByName(name).get();
            repository.delete(existing);
            return new ResponseEntity(existing, HttpStatus.OK);
        }
        return new ResponseEntity("Location by name: " + name + " not found", HttpStatus.NOT_FOUND);
    }

}
