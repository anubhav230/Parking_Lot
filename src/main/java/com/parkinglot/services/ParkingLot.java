package com.parkinglot.services;
import com.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    int parkingLotSize = 3;

    private List<String> parkingLotList = new ArrayList<>(2);

    public void park(String vehicle) throws ParkingLotException {
        if (parkingLotList.size() >= parkingLotSize)
             throw new ParkingLotException("Parking Lot is full", ParkingLotException.ExceptionType.PARKING_FULL);
        if (parkingLotList.contains(vehicle))
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException.ExceptionType.ALREADY_PARKED);
        parkingLotList.add(vehicle);
    }

    public boolean isVehicleParked(String vehicle) {
        return parkingLotList.contains(vehicle);
    }

    public boolean unPark(String car) {
        if (parkingLotList.contains(car)) {
            parkingLotList.remove(car);
            return true;
        } else return false;
    }

    public boolean isFull() {
        return parkingLotList.size() == parkingLotSize;
    }
}
