package com.parkinglot.services;

import com.parkinglot.models.Slot;
import java.util.*;

public class ParkingLot {
    int parkingLotSize;
    public Map<Integer, Slot> parkingLotMap;

    public ParkingLot(int parkingLotSize) {
        this.parkingLotSize = parkingLotSize;
        parkingLotMap = new LinkedHashMap<>();
        initialiseParkingLot(parkingLotSize);
    }

    public void initialiseParkingLot(int lotSize) {
        for (int i = 1; i <= lotSize; i++) {
            parkingLotMap.put(i, null);
        }
    }

    public int getNumberOfVehicles() {
        int numberOfVehicles = 0;
        for (int i = 1; i <= parkingLotMap.size(); i++) {
            if (parkingLotMap.get(i) != null)
                numberOfVehicles++;
        }
        return numberOfVehicles;
    }
}