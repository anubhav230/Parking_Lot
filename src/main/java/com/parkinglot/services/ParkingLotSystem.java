package com.parkinglot.services;

import com.parkinglot.Observers.ParkingLotObserver;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.models.Slot;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private final int numberOfLots;
    private final int lotSize;
    int parkingLotSize;

    public List<ParkingLotObserver> observers;
    public List<ParkingLot> parkingLots;


    public ParkingLotSystem(int lotSize, int numberOfLots) {
        parkingLots = new ArrayList<>();
        observers = new ArrayList<>();
        this.numberOfLots = numberOfLots;
        this.lotSize = lotSize;
        initialiseParkingLot(numberOfLots);
    }

    public void register(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(String vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException
                    .ExceptionType.ALREADY_PARKED);
        if (isAnySlotAvailable()) {
            observers.forEach(ParkingLotObserver::capacityIsFull);
            throw new ParkingLotException("Parking Lot is full", ParkingLotException
                    .ExceptionType.PARKING_FULL);
        }
        Slot slotValue = new Slot(vehicle, LocalTime.now().withNano(0));
        ParkingLot parkingLot = getLot(this.parkingLots);
        Integer slot1 = getSpot(parkingLot);
        parkingLot.parkingLotMap.put(slot1, slotValue);
    }

    public boolean isVehicleParked(String vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        return true;
                    }
                }
            }
        return false;
    }


    public ParkingLot getLotOfVehicle(String vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue().getVehicle().equals(vehicle)) {
                    return parkingLot;
                }
            }
        return null;
    }


    public Integer getSpot(ParkingLot parkingLot) {
        for (int i = 1; i <= parkingLot.parkingLotMap.size(); i++) {
            if (parkingLot.parkingLotMap.get(i) == null)
                return i;
        }
        return null;
    }

    public boolean isAnySlotAvailable() {
        int vehicleCount = parkingLots.stream().mapToInt(ParkingLot::getNumberOfVehicles).sum();
        boolean availability = (lotSize * numberOfLots) == vehicleCount;
        return availability;
    }

    public boolean unPark(String vehicle) {
        ParkingLot parkingLot = getLotOfVehicle(vehicle);
        if (parkingLot == null)
            return false;
        for (Map.Entry<Integer, Slot> entry : parkingLot.parkingLotMap.entrySet()) {
            if (vehicle.equals(entry.getValue().getVehicle())) {
                Integer key = entry.getKey();
                parkingLot.parkingLotMap.put(key, null);
                for (ParkingLotObserver observer : observers) {
                    observer.capacityIsAvailable();
                }
                return true;
            }
        }
        return false;
    }

    public void initialiseParkingLot(int lotSize) {
        IntStream.range(0, lotSize).forEachOrdered(i -> parkingLots.add(i, new ParkingLot(lotSize)));
    }

    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        //for (ParkingLot parkingLot : parkingLots)
        List<ParkingLot> parkingLotList = parkingLots;
        Collections.sort(parkingLotList, Comparator.comparing(parkingLot -> parkingLot.getNumberOfVehicles()));
        return parkingLotList.get(0);
    }

//    public LocalTime getParkTime(int Slot) {
//        return parkingLotMap.get(Slot).getTime();
//    }
}