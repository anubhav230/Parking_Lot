package com.parkinglot.models;

import com.parkinglot.services.ParkingLot;

public class SecurityStaff {
    public String security(ParkingLot parkingLot) {
        if (parkingLot.isFull())
            return "redirect security";
        return "space available for security vehicle";
    }
}
