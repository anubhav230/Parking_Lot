package com.parkinglot.models;

import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.Vehicle;
import com.parkinglot.enums.VehicleColor;

public class VehicleDetails {

    private final String vehicle;
    private final Vehicle vehicleType;
    private final DriverType driverType;
    private VehicleColor vehicleColor;

    public VehicleDetails(Vehicle vehicleType, DriverType driverType, VehicleColor vehicleColor, String vehicle) {

        this.vehicle = vehicle;
        this.vehicleType = vehicleType;
        this.driverType = driverType;
        this.vehicleColor = vehicleColor;
    }

    public VehicleDetails(Vehicle vehicleType, DriverType driverType, String vehicle) {
        this.vehicle = vehicle;
        this.vehicleType = vehicleType;
        this.driverType = driverType;
    }

    public VehicleColor getColor() {
        return vehicleColor;
    }

    public String getVehicle() {
        return vehicle;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public Vehicle getVehicleSize() {
        return vehicleType;
    }

}
