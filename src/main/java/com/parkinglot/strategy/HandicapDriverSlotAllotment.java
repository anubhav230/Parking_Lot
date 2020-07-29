package com.parkinglot.strategy;

import com.parkinglot.models.Slot;
import com.parkinglot.services.ParkingLot;

import java.util.List;
import java.util.Map;

public class HandicapDriverSlotAllotment implements ISlotAllotmentStrategy {

    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLotList) {
        return parkingLotList.stream().filter(parkingLot -> parkingLot.parkingSlotMap.entrySet().
                stream().anyMatch(entry -> parkingLot.parkingSlotMap.get(entry.getKey()) == null &&
                parkingLot.parkingSlotMap.get(entry.getKey() + 1) == null)).findFirst().orElse(null);
    }

}
