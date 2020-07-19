package com.parkinglot;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.models.ParkingLotOwner;
import com.parkinglot.models.SecurityStaff;
import com.parkinglot.services.ParkingLot;
import org.junit.Assert;
import org.junit.Test;


public class ParkingLotTest {

    ParkingLot parkingLot = new ParkingLot();

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() throws ParkingLotException {
        String vehicle = "vehicle1";
        parkingLot.park(vehicle);
        boolean result = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(result);
    }


    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() throws ParkingLotException {
        String vehicle = "vehicle1";
        parkingLot.park(vehicle);
        boolean result2 = parkingLot.unPark(vehicle);
        Assert.assertTrue(result2);
    }

    @Test
    public void givenCarToUnPark_WhenNotPresent_ShouldReturnFalse() throws ParkingLotException {
        String vehicle = "vehicle1";
        parkingLot.park(vehicle);
        boolean result2 = parkingLot.unPark("vehicle");
        Assert.assertFalse(result2);
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
            String vehicle = "vehicle1";
            parkingLot.park(vehicle);
            parkingLot.park(vehicle);
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
            parkingLot.park(vehicle1);
            parkingLot.park(vehicle2);
            parkingLot.park(vehicle3);
            parkingLot.park("vehicle4");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("Parking Lot is full",e.getLocalizedMessage());
        }
    }



    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurity() throws ParkingLotException {
        SecurityStaff securityStaff = new SecurityStaff();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        String vehicle3 = "vehicle3";
        parkingLot.park(vehicle1);
        parkingLot.park(vehicle2);
        parkingLot.park(vehicle3);
        Assert.assertEquals("redirect security", securityStaff.security(parkingLot));
    }

    @Test
    public void givenParkingLot_WhenHasSpace_ShouldNotRedirectSecurity() throws ParkingLotException {
        SecurityStaff securityStaff = new SecurityStaff();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        parkingLot.park(vehicle1);
        parkingLot.park(vehicle2);
        Assert.assertEquals("space available for security vehicle", securityStaff.security(parkingLot));
    }

    @Test
    public void givenParkingLot_WhenHaveSpace_ShouldPutOpenSign() throws ParkingLotException {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        String vehicle3 = "vehicle3";
        parkingLot.park(vehicle1);
        parkingLot.park(vehicle2);
        parkingLot.park(vehicle3);
        parkingLot.unPark(vehicle1);
        String result2 = parkingLotOwner.getUpdate(parkingLot);
        Assert.assertEquals("parking lot is open", result2);
    }

    @Test
    public void givenParkingLot_WhenNoSpace_ShouldPutOpenSign() throws ParkingLotException {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        String vehicle1 = "vehicle1";
        String vehicle2 = "vehicle2";
        String vehicle3 = "vehicle3";
        parkingLot.park(vehicle1);
        parkingLot.park(vehicle2);
        parkingLot.park(vehicle3);
        String result2 = parkingLotOwner.getUpdate(parkingLot);
        Assert.assertEquals("parking lot is full", result2);
    }
}
