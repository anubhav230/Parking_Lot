package com.parkinglot.models;

import java.time.LocalTime;
import java.util.Objects;

public class Slot {
    public VehicleDetails vehicleDetails;
    public LocalTime time;

    public Slot(VehicleDetails vehicleDetails, LocalTime time) {
        this.time = time;
        this.vehicleDetails = vehicleDetails;
    }

    public LocalTime getTime() {
        return time;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(vehicleDetails, slot.vehicleDetails) &&
                Objects.equals(time, slot.time);
    }

}