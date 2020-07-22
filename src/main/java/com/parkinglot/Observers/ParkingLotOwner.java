package com.parkinglot.Observers;

import com.parkinglot.services.DriverType;
import com.parkinglot.services.ParkingLotSystem;

public class ParkingLotOwner implements ParkingLotObserver {

    private boolean isFullCapacity;

    @Override
    public void capacityIsFull() {
        this.isFullCapacity = true;
    }

    @Override
    public void capacityIsAvailable() {
        this.isFullCapacity = false;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }

    public int whereToPark(ParkingLotSystem parkingLotSystem, DriverType driverType) {
        return parkingLotSystem.getSlotNumber(driverType);

    }
}