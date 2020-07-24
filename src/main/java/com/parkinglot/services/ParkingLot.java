package com.parkinglot.services;

import com.parkinglot.Observers.ParkingLotObserver;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.models.Slot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    int parkingLotSize;
    Map<Integer, Slot> parkingLotMap;
    List<ParkingLotObserver> observers;

    public ParkingLot(int lotSize) {
        observers = new ArrayList<>();
        parkingLotMap = new HashMap<>();
        initialiseParkingLot(lotSize);
    }

    public void register(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(int slot, String vehicle) throws ParkingLotException {
        if (parkingLotMap.containsValue(vehicle))
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException
                    .ExceptionType.ALREADY_PARKED);
        if (parkingLotMap.size() >= parkingLotSize && !parkingLotMap.containsValue(null)) {
            observers.forEach(ParkingLotObserver::capacityIsFull);
            throw new ParkingLotException("Parking Lot is full", ParkingLotException
                    .ExceptionType.PARKING_FULL);
        }
        Slot slot2 = new Slot(vehicle, LocalTime.now().withNano(0));
        parkingLotMap.put(slot, slot2);
    }

    public boolean isVehicleParked(String vehicle) {
        for (Map.Entry<Integer, Slot> entry : parkingLotMap.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue().getVehicle().equals(vehicle)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Integer getSpot() {
        for (int i = 1; i <= parkingLotMap.size(); i++) {
            if (parkingLotMap.get(i) == null)
                return i;
        }
        return null;
    }

    public void initialiseParkingLot(int lotSize) {
        for (int i = 1; i <= lotSize; i++) {
            parkingLotMap.put(i, null);
        }
    }

    public boolean unPark(int slot) {
        if (parkingLotMap.containsKey(slot)) {
            parkingLotMap.put(slot, null);
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsAvailable();
            }
            return true;
        }
        return false;
    }

    public LocalTime getParkTime(int Slot) {
        return parkingLotMap.get(Slot).getTime();
    }
}
