package com.parkinglot.Observers;

public class SecurityStaff implements ParkingLotObserver {

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
}
