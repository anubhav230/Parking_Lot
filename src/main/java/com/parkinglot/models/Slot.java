package com.parkinglot.models;

import java.time.LocalTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(vehicle, slot.vehicle) &&
                Objects.equals(time, slot.time);
    }
}