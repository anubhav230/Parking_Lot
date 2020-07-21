package com.parkinglotTest.Observers;

import com.parkinglotTest.services.ParkingLot;

public class ParkingLotOwner  implements ParkingLotObserver {

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

    public int whereToPark(ParkingLot parkingLotSystem) {
        return parkingLotSystem.getSlotNumber();
    }
}