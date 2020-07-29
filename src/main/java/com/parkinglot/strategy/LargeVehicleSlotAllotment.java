package com.parkinglot.strategy;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.models.Slot;
import com.parkinglot.services.ParkingLot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LargeVehicleSlotAllotment implements ISlotAllotmentStrategy {

    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLotList){
        List<ParkingLot> parkingLot = new ArrayList<>(parkingLotList);
        parkingLot.sort(Comparator.comparing(ParkingLot::getNumberOfVehicles));
        return parkingLot.get(0);
    }
}
