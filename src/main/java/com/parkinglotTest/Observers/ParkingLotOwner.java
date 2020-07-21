package com.parkinglotTest.Observers;

import java.util.HashMap;

import com.parkinglotTest.services.ParkingLot;
import com.parkinglotTest.services.ParkingLot.*;
public class ParkingLotOwner  implements ParkingLotObserver {

    ParkingLot parkingLot = new ParkingLot(3);
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