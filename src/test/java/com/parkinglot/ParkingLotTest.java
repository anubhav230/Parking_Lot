package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.models.ParkingLotOwner;
import com.parkinglot.models.SecurityStaff;
import com.parkinglot.services.ParkingLot;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParkingLotTest {

    ParkingLot parkingLot = new ParkingLot();

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() throws ParkingLotException {
        String car = "car1";
        parkingLot.park(car);
        boolean result = parkingLot.isVehicleParked(car);
        Assert.assertTrue(result);
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() throws ParkingLotException {
        String carId = "car1";
        parkingLot.park(carId);
        boolean result2 = parkingLot.unPark(carId);
        Assert.assertTrue(result2);
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            String car1 = "car1";
            String car2 = "car2";
            String car3 = "car3";
            parkingLot.park(car1);
            parkingLot.park(car2);
            parkingLot.park(car3);;
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("Parking Lot is full",e.getLocalizedMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurity() throws ParkingLotException {
        SecurityStaff securityStaff = new SecurityStaff();
        String car1 = "car1";
        String car2 = "car2";
        String car3 = "car3";
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.park(car3);
        boolean result = parkingLot.isFull();
        boolean result2 = securityStaff.security(result);
        Assert.assertTrue(result2);
    }

    @Test
    public void givenParkingLot_WhenHaveSpace_ShouldPutOpenSign() throws ParkingLotException {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        String car1 = "car1";
        String car2 = "car2";
        String car3 = "car3";
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.park(car3);
        List result = parkingLot.empty();
        String result2 = parkingLotOwner.getUpdate(result);
        Assert.assertEquals("parking lot is open", result2);
    }
}
