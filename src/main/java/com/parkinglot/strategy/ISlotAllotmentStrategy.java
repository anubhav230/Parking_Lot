package com.parkinglot.strategy;
import com.parkinglot.services.ParkingLot;

import java.util.List;

public interface ISlotAllotmentStrategy {
    ParkingLot getLot(List<ParkingLot> parkingLotList);
}
