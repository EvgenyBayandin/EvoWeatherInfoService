package ru.webdev.person.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.webdev.person.model.User;

@Repository
public interface PersonRepository extends CrudRepository<User, Integer> {
}
