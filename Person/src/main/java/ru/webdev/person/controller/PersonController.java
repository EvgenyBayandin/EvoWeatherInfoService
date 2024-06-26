package ru.webdev.person.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.webdev.person.model.Person;
import ru.webdev.person.model.Weather;
import ru.webdev.person.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<Iterable<Person>> findAll() {
        if (repository.findAll().iterator().hasNext()) {
            return new ResponseEntity(repository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity("No persons found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> findById(@PathVariable int id) {
        if (repository.existsById(id)) {
            return new ResponseEntity(repository.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity("Person by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable int id, @RequestBody Person person) {
        if (repository.existsById(id)) {
            Person p = repository.findById(id).get();
            p.setFirstname(person.getFirstname());
            p.setLastname(person.getLastname());
            p.setSurname(person.getSurname());
            p.setBirthdate(person.getBirthdate());
            p.setLocation(person.getLocation());
            repository.save(p);
            return new ResponseEntity(p, HttpStatus.OK);
        }
        return new ResponseEntity("Person by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity("Person by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/weather")
    public ResponseEntity<Weather> getWeather(@PathVariable int id) {
        if (repository.existsById(id)) {
            String location = repository.findById(id).get().getLocation();
            Weather weather = restTemplate.getForObject("http://localhost:8083/location/weather?name=" + location, Weather.class);
            return new ResponseEntity(weather, HttpStatus.OK);
        }
        return new ResponseEntity("Person by ID: " + id + " not found.", HttpStatus.NOT_FOUND);
    }


}
