package com.parkinglotTest;

import com.parkinglotTest.Observers.ParkingLotOwner;
import com.parkinglotTest.Observers.SecurityStaff;
import com.parkinglotTest.exception.ParkingLotException;
import com.parkinglotTest.services.ParkingLot;
import com.parkinglotTest.services.Slot;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class ParkingLotTest {

    ParkingLot parkingLot = new ParkingLot(3);

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() {
        try {
            parkingLot.park(1, "vehicle1");
            boolean result = parkingLot.isVehicleParked("vehicle1");
            Assert.assertTrue(result);
        } catch (ParkingLotException e) {
           e.printStackTrace();
       }
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() {
        try {
            parkingLot.park(1, "vehicle");
            boolean result2 = parkingLot.unPark(1);
            Assert.assertTrue(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToUnPark_WhenNotPresent_ShouldReturnFalse() {
        try {
            parkingLot.park(1, "vehicle");
            boolean result2 = parkingLot.unPark(4);
            Assert.assertFalse(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.park(1, "vehicle");
            parkingLot.park(2, "vehicle");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("Parking Lot is full", e.getLocalizedMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLot.register(securityStaff);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            boolean result = securityStaff.isCapacityFull();
            Assert.assertTrue(result);
        }
    }

    @Test
    public void givenParkingLot_WhenHasSpace_ShouldNotRedirectSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLot.register(securityStaff);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            parkingLot.unPark(3);
            boolean fullCapacity = securityStaff.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }
    }

    @Test
    public void givenParkingLot_WhenHaveSpace_ShouldPutCloseSign() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(fullCapacity);
        }
    }

    @Test
    public void givenParkingLot_WhenNoSpace_ShouldPutOpenSign() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            parkingLot.unPark(3);
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }

    }

    @Test
    public void givenVehicle_ShouldParkOnEmptySlot() {
        try {
            int result = parkingLot.getSpot();
            parkingLot.park(result, "vehicle");
            boolean result3 = parkingLot.isVehicleParked("vehicle");
            Assert.assertTrue(result3);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_shouldReturnParkedTime() {
        try {
            int spot = parkingLot.getSpot();
            parkingLot.park(spot, "vehicle2");
            LocalTime time = parkingLot.getParkTime(spot);
            Assert.assertEquals(LocalTime.now().withNano(0), time.withNano(0));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}
