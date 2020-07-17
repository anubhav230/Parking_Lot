package com.parkinglotTest;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

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
            return false;
    }
}
