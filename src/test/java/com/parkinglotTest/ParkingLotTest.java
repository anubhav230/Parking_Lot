package com.parkinglotTest;

import com.parkinglotTest.exception.ParkingLotException;
import com.parkinglotTest.models.ParkingLotOwner;
import com.parkinglotTest.models.SecurityStaff;
import com.parkinglotTest.services.ParkingLot;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot = new ParkingLot(3);

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() throws ParkingLotException {
        int slot = parkingLot.getSlotNumber();
        String vehicle = "vehicle1";
        parkingLot.park(slot, vehicle);
        boolean result = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(result);
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() throws ParkingLotException {
        int slot = parkingLot.getSlotNumber();
        String vehicle = "vehicle1";
        parkingLot.park(slot, vehicle);
        boolean result2 = parkingLot.unPark(slot);
        Assert.assertTrue(result2);
    }

    @Test
    public void givenCarToUnPark_WhenNotPresent_ShouldReturnFalse() throws ParkingLotException {
        int slot = parkingLot.getSlotNumber();
        String vehicle = "vehicle1";
        parkingLot.park(slot, vehicle);
        boolean result2 = parkingLot.unPark(3);
        Assert.assertFalse(result2);
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
            //int slot = parkingLot.getSlotNumber();
            String vehicle = "vehicle1";
            parkingLot.park(1, vehicle);
            parkingLot.park(1, vehicle);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            String vehicle1 = "vehicle1";
            String vehicle2 = "vehicle2";
            String vehicle3 = "vehicle3";
            parkingLot.park(1, vehicle1);
            parkingLot.park(2, vehicle2);
            parkingLot.park(3, vehicle3);
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("Parking Lot is full", e.getLocalizedMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurity() throws ParkingLotException {
        SecurityStaff securityStaff = new SecurityStaff();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        String vehicle3 = "vehicle3";
        parkingLot.park(1, vehicle1);
        parkingLot.park(2, vehicle2);
        parkingLot.park(3, vehicle3);
        Assert.assertEquals("redirect security", securityStaff.security(parkingLot));
    }

    @Test
    public void givenParkingLot_WhenHasSpace_ShouldNotRedirectSecurity() throws ParkingLotException {
        SecurityStaff securityStaff = new SecurityStaff();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        parkingLot.park(1, vehicle1);
        parkingLot.park(2, vehicle2);
        Assert.assertEquals("space available for vehicle", securityStaff.security(parkingLot));
    }

    @Test
    public void givenParkingLot_WhenHaveSpace_ShouldPutOpenSign() throws ParkingLotException {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        String vehicle3 = "vehicle3";
        parkingLot.park(1, vehicle1);
        parkingLot.park(2, vehicle2);
        parkingLot.park(3, vehicle3);
        parkingLot.unPark(1);
        String result2 = parkingLotOwner.getUpdate(parkingLot);
        Assert.assertEquals("parking lot is open", result2);
    }

    @Test
    public void givenParkingLot_WhenNoSpace_ShouldPutOpenSign() throws ParkingLotException {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        String vehicle3 = "vehicle3";
        parkingLot.park(1, vehicle1);
        parkingLot.park(2, vehicle2);
        parkingLot.park(3, vehicle3);
        String result2 = parkingLotOwner.getUpdate(parkingLot);
        Assert.assertEquals("parking lot is full", result2);
    }

    @Test
    public void givenCarAndUser2_WhenPark_ShouldPark() throws ParkingLotException {
        int result = parkingLot.getSlotNumber();
        parkingLot.park(result, "vehicle");
        boolean result2 = parkingLot.isVehicleParked("vehicle");
        Assert.assertTrue(result2);
    }

}
