package com.eleazar.polling.models;

import lombok.Data;

@Data
public class Coordinates {
    private double latitude;
    private double longitude;

    public Coordinates(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public Coordinates(){
    }
}
