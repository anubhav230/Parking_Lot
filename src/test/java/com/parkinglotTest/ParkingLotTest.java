package com.parkinglotTest;

import com.parkinglotTest.Observers.Attendant;
import com.parkinglotTest.exception.ParkingLotException;
import com.parkinglotTest.Observers.ParkingLotOwner;
import com.parkinglotTest.Observers.SecurityStaff;
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
        boolean result2 = parkingLot.unPark(4);
        Assert.assertFalse(result2);
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
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
        parkingLot.register(securityStaff);
        try {
            String vehicle1 = "vehicle1";
            String vehicle2 = "vehicle2";
            String vehicle3 = "vehicle3";
            String vehicle4 = "vehicle4";
            parkingLot.park(1, vehicle1);
            parkingLot.park(2, vehicle2);
            parkingLot.park(3, vehicle3);
            parkingLot.park(4, vehicle4);
        }catch (ParkingLotException e) {
            boolean result = securityStaff.isCapacityFull();
            Assert.assertTrue(result);
        }
    }

    @Test
    public void givenParkingLot_WhenHasSpace_ShouldNotRedirectSecurity() throws ParkingLotException {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLot.register(securityStaff);
        try {
            String vehicle1 = "vehicle1";
            String vehicle2 = "vehicle2";
            parkingLot.park(1, vehicle1);
            parkingLot.park(2, vehicle2);
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
            //Assert.assertEquals("space available for vehicle", securityStaff.security(parkingLot));
        } catch (ParkingLotException e) {
            parkingLot.unPark(3);
            boolean fullCapacity = securityStaff.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }
    }


//    @Test
//    public void givenParkingLot_WhenHaveSpace_ShouldPutOpenSign() throws ParkingLotException {
//        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
//        String vehicle1 = "vehicle1";
//        String vehicle2 = "vehicle2";
//        String vehicle3 = "vehicle3";
//        parkingLot.park(1, vehicle1);
//        parkingLot.park(2, vehicle2);
//        parkingLot.park(3, vehicle3);
//        parkingLot.unPark(1);
//        String result2 = parkingLotOwner.getUpdate(parkingLot);
//        Assert.assertEquals("parking lot is open", result2);
//    }

    @Test
    public void givenParkingLot_WhenNoSpace_ShouldPutOpenSign() throws ParkingLotException {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        try {
            String vehicle1 = "vehicle1";
            String vehicle2 = "vehicle2";
            String vehicle3 = "vehicle3";
            String vehicle4 = "vehicle4";
            parkingLot.park(1, vehicle1);
            parkingLot.park(2, vehicle2);
            parkingLot.park(3, vehicle3);
            parkingLot.park(4, vehicle4);
        } catch (ParkingLotException e) {
            parkingLot.unPark(3);
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }

    }

    @Test
    public void givenCarAndUser2_WhenPark_ShouldPark() throws ParkingLotException {
        Attendant attendant = new Attendant();
        int slot = attendant.parkingSlot(parkingLot);
        parkingLot.park(slot, "vehicle");
        boolean result2 = parkingLot.isVehicleParked("vehicle");
        Assert.assertTrue(result2);
    }
}
