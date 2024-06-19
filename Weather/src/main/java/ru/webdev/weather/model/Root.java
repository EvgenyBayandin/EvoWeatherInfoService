package ru.webdev.weather.model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Root {

    private Coord coord;

    private ArrayList<Weather> weather;

    private String base;

    private Main main;

    private int visibility;

    private Wind wind;

    private Clouds clouds;

    private int dt;

    private Sys sys;

    private int timezone;

    private int id;

    private String name;

    private int cod;

}
