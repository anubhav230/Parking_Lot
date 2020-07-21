package com.parkinglot.Observers;


import com.parkinglot.services.ParkingLotSystem;

public class Attendant {
    public int parkingSlot(ParkingLotSystem parkingLot) {
        return new ParkingLotOwner().whereToPark(parkingLot);
    }
}
