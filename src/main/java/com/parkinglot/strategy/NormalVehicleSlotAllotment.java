package com.parkinglot.strategy;
import com.parkinglot.services.ParkingLot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NormalVehicleSlotAllotment implements ISlotAllotmentStrategy {

    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLotList) {
        List<ParkingLot> parkingLot = new ArrayList<>(parkingLotList);
        parkingLot.sort(Comparator.comparing(ParkingLot::getNumberOfVehicles));
        return parkingLot.get(0);
    }
}
