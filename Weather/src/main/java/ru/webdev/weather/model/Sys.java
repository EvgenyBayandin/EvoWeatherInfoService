package ru.webdev.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sys {

    private String country;

    private int sunrise;

    private int sunset;

}
