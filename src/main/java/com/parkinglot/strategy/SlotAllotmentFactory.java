package com.parkinglot.strategy;

import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.Vehicle;
import com.parkinglot.models.VehicleDetails;

public class SlotAllotmentFactory {
    public ISlotAllotmentStrategy lotAllotment(VehicleDetails vehicleDetails) {
        return vehicleDetails.getVehicleSize() == Vehicle.SMALL ?
                vehicleDetails.getDriverType() == DriverType.NORMAL ?
                         new NormalVehicleSlotAllotment() : new HandicapDriverSlotAllotment() : new LargeVehicleSlotAllotment();
    }
}
