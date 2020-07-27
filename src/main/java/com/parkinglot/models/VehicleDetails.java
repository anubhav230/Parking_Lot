package com.parkinglot.models;

//import com.parkinglot.enums.CarCompany;
//import com.parkinglot.enums.DriverType;
//import com.parkinglot.enums.Vehicle;
//import com.parkinglot.enums.VehicleColor;
import com.parkinglot.enums.AllEnums.CarCompany;
import com.parkinglot.enums.AllEnums.DriverType;
import com.parkinglot.enums.AllEnums.VehicleColor;
import com.parkinglot.enums.AllEnums.Vehicle;

public class VehicleDetails {

    private final String vehicle;
    private final Vehicle vehicleType;
    private DriverType driverType;
    private CarCompany carCompany;
    private VehicleColor vehicleColor;

    public VehicleDetails(Vehicle vehicleType, VehicleColor vehicleColor,
                          CarCompany carCompany, String vehicle ) {
        this.vehicle = vehicle;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
        this.carCompany = carCompany;
    }

    public VehicleDetails(Vehicle vehicleType, DriverType driverType,
                          VehicleColor vehicleColor, String vehicle) {

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

    public CarCompany getCarCompany() {
        return carCompany;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public Vehicle getVehicleSize() {
        return vehicleType;
    }

}
