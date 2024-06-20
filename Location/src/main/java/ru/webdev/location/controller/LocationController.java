package ru.webdev.location.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.webdev.location.model.Geodata;
import ru.webdev.location.repository.GeodataRepository;

@RestController("/location")
public class LocationController {

    @Autowired
    private GeodataRepository repository;

    @GetMapping
    public Optional<Geodata> getLocation(@RequestParam String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Geodata save(@RequestBody Geodata geodata) {
        return repository.save(geodata);
    }

}
