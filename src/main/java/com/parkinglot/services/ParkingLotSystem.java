package com.parkinglot.services;

import com.parkinglot.Observers.ParkingLotObserver;
import com.parkinglot.exception.ParkingLotException;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalTime;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    Map<Integer, String> parkingLotMap;
    List<ParkingLotObserver> observers;
    public LocalTime parkTime = null;
    public LocalTime unParkTime = null;
    int parkingLotSize;
    private int slot = 1;

    public ParkingLotSystem(int lotSize) {
        observers = new ArrayList<>();
        parkingLotMap = new HashMap<>();
        this.parkingLotSize = lotSize;
        initialiseParkingLot();
    }

    public void register(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(int slot, String vehicle) throws ParkingLotException {
        if (parkingLotMap.containsValue(vehicle))
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException.ExceptionType.ALREADY_PARKED);
        if (parkingLotMap.size() >= parkingLotSize && !parkingLotMap.containsValue(" ")) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException("Parking Lot is full", ParkingLotException.ExceptionType
                    .PARKING_FULL);
        }
        parkingLotMap.put(slot, vehicle);
        parkTime = LocalTime.now();
        this.slot = slot;

    }

    public boolean isVehicleParked(String vehicle) {
        return parkingLotMap.containsValue(vehicle);
    }

    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue()))
                return entry.getKey();
        }
        return null;
    }

    public int getSlotNumber() {
        return getKey(parkingLotMap, " ");
    }

    public int getVehicleValue(String value) {
        return getKey(parkingLotMap, value);
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

    public int getSlotNumber(DriverType driverType) {
        if (DriverType.HANDICAP == driverType)
            return getSlotNumber();
        return this.slot++;
    }

    public void initialiseParkingLot() {
        IntStream.rangeClosed(1, parkingLotSize).forEach(i -> parkingLotMap.put(i, " "));
    }

}
