package ru.webdev.person.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.person.model.Person;
import ru.webdev.person.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public Iterable<Person> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id){
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person){
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()),HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }

}
