package com.parkinglotTest.services;

import com.parkinglotTest.exception.ParkingLotException;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {

    int parkingLotSize = 3;

    Map<Integer, String> parkingLotMap = new HashMap<>();

    public ParkingLot(int lotSize) {
        for (int i = 1; i <= lotSize; i++) {
            parkingLotMap.put(i, " ");
        }
    }

    public void park(int slot, String vehicle) throws ParkingLotException {
        if (parkingLotMap.size() >= parkingLotSize && !parkingLotMap.containsValue(" "))
            throw new ParkingLotException("Parking Lot is full", ParkingLotException.ExceptionType.PARKING_FULL);
        if (parkingLotMap.containsValue(vehicle))
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException.ExceptionType.ALREADY_PARKED);
        parkingLotMap.put(slot, vehicle);
        System.out.println(parkingLotMap);

    }

    public boolean isVehicleParked(String vehicle) {
        return parkingLotMap.containsValue(vehicle);
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int getSlotNumber() {
        return getKey(parkingLotMap, " ");
    }

    public boolean unPark(int slot) {
        if (parkingLotMap.containsKey(slot)) {
            parkingLotMap.put(slot, " ");
            System.out.println(parkingLotMap);
            return true;
        } else return false;
    }

    public boolean isFull() {
        return parkingLotMap.size() >= parkingLotSize && !parkingLotMap.containsValue(" ");
    }
}
