package com.parkinglot.models;

import java.time.LocalTime;

public class Slot {
    public VehicleDetails vehicleDetails;
    public LocalTime time;
    private final String attendantName;

    public Slot(VehicleDetails vehicleDetails, LocalTime time, String attendantName) {
        this.time = time;
        this.vehicleDetails = vehicleDetails;
        this.attendantName = attendantName;
    }

    public String getAttendantName() {
        return attendantName;
    }

    public LocalTime getTime() {
        return time;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }
}