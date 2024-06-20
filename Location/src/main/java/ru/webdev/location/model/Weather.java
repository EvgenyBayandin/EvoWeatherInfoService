package ru.webdev.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Weather {

    private double temp;

    private double feels_like;

    private double temp_min;

    private double temp_max;

    private int pressure;

    private int humidity;

    private int sea_level;

    private int grnd_level;

}
