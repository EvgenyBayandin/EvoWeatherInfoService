package ru.webdev.person.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
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
    private LocalDate birthday;

    @NonNull
    private String location;


}
