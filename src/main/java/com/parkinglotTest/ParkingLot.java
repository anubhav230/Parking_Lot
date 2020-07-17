package com.parkinglotTest;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    List<String> parkingLot = new ArrayList<>();
    public int park(String car) {
        parkingLot.add(car);
        return parkingLot.size();
    }

    public boolean unPark(String car) {
        return false;
    }
}
