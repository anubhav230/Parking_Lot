package com.parkinglot.models;

import java.time.LocalTime;

public class Slot {
    public VehicleDetails vehicleDetails;
    public LocalTime time;
    private String attendantNme;

    public Slot(VehicleDetails vehicleDetails, LocalTime time, String attendantNme) {
        this.time = time;
        this.vehicleDetails = vehicleDetails;
        this.attendantNme = attendantNme;
    }

    public String getAttendantName() {
        return attendantNme;
    }

    public LocalTime getTime() {
        return time;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }
}