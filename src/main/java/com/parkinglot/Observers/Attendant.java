package com.parkinglot.Observers;

import com.parkinglot.services.DriverType;

import com.parkinglot.services.ParkingLotSystem;

public class Attendant {

    public int parkingSlot(ParkingLotSystem parkingLot, DriverType driverType) {
        return new ParkingLotOwner().whereToPark(parkingLot, driverType);
    }
}
