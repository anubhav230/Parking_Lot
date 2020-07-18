package com.parkinglot.models;

import java.util.List;

public class ParkingLotOwner {
    public String getUpdate(List list) {
        if (list.size() < 3)
            return "parking lot is open";
        else return  "parking lot is full";
    }
}
