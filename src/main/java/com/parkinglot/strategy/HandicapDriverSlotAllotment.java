package com.parkinglot.strategy;

import com.parkinglot.models.Slot;
import com.parkinglot.services.ParkingLot;

import java.util.List;
import java.util.Map;

public class HandicapDriverSlotAllotment implements ISlotAllotmentStrategy {

    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLotList) {
        for (ParkingLot parkingLot : parkingLotList)
            for (Map.Entry<Integer, Slot> entry : parkingLot.parkingSlotMap.entrySet()) {
                int key = entry.getKey();
                if (parkingLot.parkingSlotMap.get(key) == null &&
                        parkingLot.parkingSlotMap.get(key + 1) == null) return parkingLot;
            }
        return null;
    }

}
