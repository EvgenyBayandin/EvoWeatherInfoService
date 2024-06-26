package ru.webdev.person.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NonNull
    private String firstname;

    @NonNull
    private String surname;

    @NonNull
    private String lastname;

    @NonNull
    private LocalDate birthdate;

    @NonNull
    private String location;

    public User(@NonNull String firstname, @NonNull String surname, @NonNull String lastname, @NonNull LocalDate birthdate, @NonNull String location) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.location = location;
    }
}
