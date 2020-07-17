package com.parkinglot.services;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    int parkingLotSize = 3;

    List<String> parkingLot = new ArrayList<>();

    public int park(String... car) {
        for (String a : car){
            parkingLot.add(a);
        }
        return parkingLot.size();
    }

    public boolean unPark(String car) {
        if (parkingLot.contains(car)) {
            parkingLot.remove(car);
            return true;
        } else return false;
    }

    public boolean isFull() {
        if (parkingLot.size() == parkingLotSize )
            return true;
        return false;
    }

    public List empty() {
        parkingLot.remove(parkingLot.size() - 1);
        return parkingLot;
    }
}
