
package com.parkinglot.services;

import com.parkinglot.Observers.ParkingLotObserver;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.models.Slot;

import java.time.LocalTime;
import java.util.*;

public class ParkingLotSystem {
    private final int numberOfLots;
    private final int lotSize;

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

    public void park(DriverType driverType, String vehicle) throws ParkingLotException {
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

    public Integer getSpot(ParkingLot parkingLot) {
        for (int i = 1; i <= parkingLot.parkingLotMap.size(); i++) {
            if (parkingLot.parkingLotMap.get(i) == null)
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
        ParkingLot parkingLot = vehicleLot(vehicle);
        if (parkingLot == null)
            return false;
        for (Map.Entry<Integer, Slot> entry : parkingLot.parkingLotMap.entrySet()) {
            if (vehicle.equals(entry.getValue().getVehicle())) {
                Integer key = entry.getKey();
                parkingLot.parkingLotMap.put(key, null);
                observers.forEach(ParkingLotObserver::capacityIsAvailable);
                return true;
            }
        }
        return false;
    }

    public ParkingLot vehicleLot(String vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue().getVehicle().equals(vehicle)) {
                    return parkingLot;
                }
            }
        return null;
    }

    public void initialiseParkingLot(int lotSize) {
        for (int i = 0; i < lotSize; i++) {
            parkingLots.add(i, new ParkingLot(lotSize));
        }
    }

    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        List<ParkingLot> parkingLotList = parkingLots;
        parkingLots.sort(Comparator.comparing(ParkingLot::getNumberOfVehicles));
        return parkingLotList.get(0);
    }

    public LocalTime getParkTime(int vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            return parkingLot.parkingLotMap.get(vehicle).getTime();
        return null;
    }


}