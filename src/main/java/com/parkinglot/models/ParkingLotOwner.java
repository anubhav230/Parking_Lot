package com.parkinglot.models;

import com.parkinglot.services.ParkingLot;

import java.util.List;

public class ParkingLotOwner {


    public String getUpdate(ParkingLot parkingLot) {
        if (!parkingLot.isFull())
            return "parking lot is open";
        return  "parking lot is full";
    }
}