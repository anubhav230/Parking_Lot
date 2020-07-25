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

    private List<ParkingLotObserver> observers;
    private List<ParkingLot> parkingLots;

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
        if (isSizeFull()) {
            observers.forEach(ParkingLotObserver::capacityIsFull);
            throw new ParkingLotException("Parking Lot is full", ParkingLotException
                    .ExceptionType.PARKING_FULL);
        }
        Slot slotValue = new Slot(vehicle, LocalTime.now().withNano(0));
        ParkingLot parkingLot = getLot(this.parkingLots);
        Integer slot1 = getSpot(parkingLot);
        parkingLot.parkingSlotMap.put(slot1, slotValue);
    }

    public boolean isVehicleParked(String vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue()
                            .getVehicle().equals(vehicle)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Integer getSpot(ParkingLot parkingLot) {
        for (int i = 1; i <= parkingLot.parkingSlotMap.size(); i++) {
            if (parkingLot.parkingSlotMap.get(i) == null)
                return i;
        }
        return null;
    }

    public boolean isSizeFull() {
        int vehicleCount = parkingLots.stream().mapToInt(ParkingLot::getNumberOfVehicles).sum();
        boolean availability = (lotSize * numberOfLots) == vehicleCount;
        return availability;
    }

    public boolean unPark(String vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        parkingLot.parkingSlotMap.put(key, null);
                        observers.forEach(ParkingLotObserver::capacityIsAvailable);
                        return true;
                    }
                }
            }
        return false;
    }

    public void initialiseParkingLot(int lotSize) {
        IntStream.range(0, lotSize).forEach(i -> parkingLots
                .add(i, new ParkingLot(lotSize)));
    }

    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        List<ParkingLot> parkingLotList = parkingLots;
        parkingLots.sort(Comparator.comparing(ParkingLot::getNumberOfVehicles));
        return parkingLotList.get(0);
    }

    public LocalTime getParkTime(String vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        return parkingLot.parkingSlotMap.get(key).getTime();
                    }
                }
            }
        return null;
    }


    public String vehicleLocation(String vehicle) {
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            lot++;
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        return +lot + "," + "" + key;
                    }
                }
            }
        }
        return null;
    }
}