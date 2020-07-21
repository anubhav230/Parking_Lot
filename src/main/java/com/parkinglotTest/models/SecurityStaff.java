package com.parkinglotTest.models;

import com.parkinglotTest.services.ParkingLot;

public class SecurityStaff {
    public String security(ParkingLot parkingLot) {
        if (parkingLot.isFull())
            return "redirect security";
        return "space available for vehicle";
    }
}
