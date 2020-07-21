package com.parkinglotTest.models;

import com.parkinglotTest.services.ParkingLot;

public class ParkingLotOwner {

    public String getUpdate(ParkingLot parkingLot) {
        if (!parkingLot.isFull())
            return "parking lot is open";
        return  "parking lot is full";
    }
}