package ru.webdev.person.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.webdev.person.model.User;
import ru.webdev.person.model.Weather;
import ru.webdev.person.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

//    private final String url = "http://location-service/location/weather?name=";

    @Value("${location.url}")
    private String url;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        if (repository.findAll().iterator().hasNext()) {
            return new ResponseEntity(repository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity("No persons found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable int id) {
        if (repository.existsById(id)) {
            return new ResponseEntity(repository.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity("User by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return repository.findById(user.getId()).isPresent()
                ? new ResponseEntity(repository.findById(user.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user) {
        if (repository.existsById(id)) {
            User p = repository.findById(id).get();
            p.setFirstname(user.getFirstname());
            p.setLastname(user.getLastname());
            p.setSurname(user.getSurname());
            p.setBirthdate(user.getBirthdate());
            p.setLocation(user.getLocation());
            repository.save(p);
            return new ResponseEntity(p, HttpStatus.OK);
        }
        return new ResponseEntity("User by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity("User by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/weather")
    public ResponseEntity<Weather> getWeather(@PathVariable int id) {
        if (repository.existsById(id)) {
            String location = repository.findById(id).get().getLocation();
            String locationUrl = String.format("http://%s/location/weather?name=%s", url, location);
            Weather weather = restTemplate.getForObject(locationUrl, Weather.class);
            return new ResponseEntity(weather, HttpStatus.OK);
        }
        return new ResponseEntity("User by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }


}
