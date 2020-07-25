package com.parkinglot.models;

import java.time.LocalTime;

public class Slot {
    public String vehicle;
    public LocalTime time;

    public Slot(String vehicle, LocalTime time) {
        this.time = time;
        this.vehicle = vehicle;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getVehicle() {
        return vehicle;
    }
}
