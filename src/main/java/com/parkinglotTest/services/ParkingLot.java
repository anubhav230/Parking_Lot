package com.parkinglotTest.services;

import com.parkinglotTest.Observers.ParkingLotObserver;
import com.parkinglotTest.exception.ParkingLotException;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.LocalTime;

public class ParkingLot {
    public LocalTime parkTime = null;
    public LocalTime unParkTime = null;
    int parkingLotSize = 3;
    List<ParkingLot> parkingLotList = new ArrayList<>();

    Map<Integer, String> parkingLotMap;
    List<ParkingLotObserver> observers;

    public ParkingLot(int lotSize) {
        observers = new ArrayList<>();
        parkingLotMap = new HashMap<>();
        initializeParkingLot(lotSize);
    }

    public void register(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(int slot, String vehicle) throws ParkingLotException {
        if (parkingLotMap.containsValue(vehicle))
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException
                    .ExceptionType.ALREADY_PARKED);
        if (parkingLotMap.size() >= parkingLotSize && !parkingLotMap.containsValue(" ")) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException("Parking Lot is full", ParkingLotException
                    .ExceptionType.PARKING_FULL);
        }
        parkingLotMap.put(slot, vehicle);
        parkTime = LocalTime.now();
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

    public int getVehicleValue(String value) {
        return getKey(parkingLotMap, value);

    }

    public void initializeParkingLot(int lotSize) {
        for (int i = 1; i <= lotSize; i++) {
            parkingLotMap.put(i, " ");
        }
    }

    public boolean unPark(int slot) {
        if (parkingLotMap.containsKey(slot)) {
            parkingLotMap.put(slot, " ");
            unParkTime = LocalTime.now();
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsAvailable();
            }
            return true;
        }
        return false;
    }
}
