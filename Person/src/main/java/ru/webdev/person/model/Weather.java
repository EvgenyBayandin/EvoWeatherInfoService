package ru.webdev.person.model;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
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
