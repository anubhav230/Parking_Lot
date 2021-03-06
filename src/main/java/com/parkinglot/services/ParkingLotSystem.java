package com.parkinglot.services;

import com.parkinglot.Observers.ParkingLotObserver;
import com.parkinglot.enums.CarCompany;
import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.VehicleColor;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.models.Slot;
import com.parkinglot.models.VehicleDetails;
import com.parkinglot.strategy.SlotAllotmentFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private final int numberOfLots;
    private final int lotSize;
    private final List<ParkingLotObserver> observers;
    public final List<ParkingLot> parkingLots;
    //private Integer key;

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

    public void park(VehicleDetails vehicleDetails, String attendantName) throws ParkingLotException {
        if (isVehicleParked(vehicleDetails.getVehicle()))
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException
                    .ExceptionType.ALREADY_PARKED);
        if (isSizeFull()) {
            observers.forEach(ParkingLotObserver::capacityIsFull);
            throw new ParkingLotException("Parking Lot is full", ParkingLotException
                    .ExceptionType.PARKING_FULL);
        }
        Slot slotValue = new Slot(vehicleDetails, LocalTime.now().withNano(0), attendantName);
        ParkingLot parkingLot = new SlotAllotmentFactory().lotAllotment(vehicleDetails).getLot(parkingLots);
        Integer slot = this.getSpot(parkingLot);
        parkingLot.parkingSlotMap.put(slot, slotValue);
    }

    public boolean isVehicleParked(String vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet())
                if (entry.getValue() == null || !entry.getValue().getVehicleDetails()
                        .getVehicle().equals(vehicle)) {
                } else {
                    return true;
                }
        }
        return false;
    }

    public Integer getSpot(ParkingLot parkingLot) {
        for (int i = 1; i <= parkingLot.parkingSlotMap.size(); i++) {
            if (parkingLot.parkingSlotMap.get(i) == null) return i;
        }
        return null;
    }

    public boolean isSizeFull() {
        int vehicleCount = parkingLots.stream().mapToInt(ParkingLot::getNumberOfVehicles).sum();
        return (lotSize * numberOfLots) == vehicleCount;
    }

    public boolean unPark(String vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
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

    public LocalTime getParkTime(String vehicle) {
        for (ParkingLot parkingLot : parkingLots)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                    Integer key = entry.getKey();
                    return parkingLot.parkingSlotMap.get(key).getTime();
                }
            }
        return null;
    }

    public String vehicleLocation(String vehicle) {
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            lot++;
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails()
                        .getVehicle().equals(vehicle)) {
                    Integer key = entry.getKey();
                    return +lot + "," + "" + key;
                }
            }
        }
        return null;
    }

    public List findWhiteVehiclePosition(VehicleColor color) {
        List<String> whiteVehicleDetails = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            lot++;
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getColor().equals(color)) {
                    Integer key = entry.getKey();
                    String location = "L" + lot + ", S" + key;
                    whiteVehicleDetails.add(location);
                }
            }
        }
        return whiteVehicleDetails;
    }

    public List<String> findPositionColorAndCompanyOfCar(VehicleColor color, CarCompany carCompany) {
        List<String> vehicleDetails = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            lot++;
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet())
                if (entry.getValue() != null && entry.getValue()
                        .getVehicleDetails().getColor().equals(color) &&
                        entry.getValue().getVehicleDetails().getCarCompany().equals(carCompany)) {
                    Integer key = entry.getKey();
                    String details = "L: " + lot + ", S: " + key + ", Number: " + entry.getValue()
                            .getVehicleDetails().getVehicle() + ", Attendant Name: " +
                            entry.getValue().getAttendantName();
                    vehicleDetails.add(details);
                }
        }
        return vehicleDetails;
    }

    public int parkedVehicleCount(CarCompany company) {
        return parkingLots.stream().mapToInt(parkingLot -> (int) parkingLot.parkingSlotMap
                        .entrySet().stream().filter(entry -> entry.getValue() != null &&
                        entry.getValue().getVehicleDetails().getCarCompany().equals(company))
                        .count()).sum();
    }

    public List<String> ParkTimeDuration(int timeInMinutes) {
        List<String> vehicleDetails = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            lot++;
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet())
                if (entry.getValue() != null && Duration.between(entry.getValue().getTime(),
                        LocalDateTime.now()).toMinutes() <= timeInMinutes) {
                    Integer key = entry.getKey();
                    String details = "L: " + lot + ", S: " + key;
                    vehicleDetails.add(details);
                }
            return vehicleDetails;
        }
        return null;
    }

    public List<String> locationAndInformationOfDriverType(DriverType driverType, int lot2) {
        List<String> vehicleDetails = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            lot++;
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet())
                if (entry.getValue() != null && lot == lot2 &&
                        entry.getValue().getVehicleDetails().getDriverType().equals(driverType)) {
                    Integer slot = entry.getKey();
                    String details = "L: " + lot + ", S: " + slot + ", " +
                            entry.getValue().getVehicleDetails().getVehicle();
                    vehicleDetails.add(details);
                }
            return vehicleDetails;
        }
        return null;
    }

    public int carCount() {
        int sum = parkingLots.stream().mapToInt(parkingLot -> (int) parkingLot
                .parkingSlotMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null).count()).sum();
        return sum;

    }
}