package com.parkinglot.services;

import com.parkinglot.models.Slot;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLot {
    int parkingLotSize;
    public Map<Integer, Slot> parkingSlotMap;

    public ParkingLot(int parkingLotSize) {
        this.parkingLotSize = parkingLotSize;
        parkingSlotMap = new LinkedHashMap<>();
        initialiseParkingLot(parkingLotSize);
    }

    public void initialiseParkingLot(int lotSize) {
        IntStream.rangeClosed(1, lotSize)
                .forEach(i -> parkingSlotMap.put(i, null));
    }

    public int getNumberOfVehicles() {
        int numberOfVehicles = (int) IntStream.rangeClosed(1, parkingSlotMap
                .size()).filter(i -> parkingSlotMap.get(i) != null).count();
        return numberOfVehicles;
    }
}
