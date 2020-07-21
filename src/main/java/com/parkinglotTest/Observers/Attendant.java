package com.parkinglotTest.Observers;

import com.parkinglotTest.services.ParkingLot;

public class Attendant {
    public int parkingSlot(ParkingLot parkingLot) {
        return new ParkingLotOwner().whereToPark(parkingLot);
    }
}
