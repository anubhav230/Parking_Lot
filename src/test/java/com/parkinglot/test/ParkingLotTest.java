package com.parkinglot.test;

import com.parkinglot.Observers.ParkingLotOwner;
import com.parkinglot.Observers.SecurityStaff;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.services.DriverType;
import com.parkinglot.services.ParkingLotSystem;
import com.parkinglot.services.Vehicle;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class ParkingLotTest {

    ParkingLotSystem parkingLots = new ParkingLotSystem(3, 1);

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() {
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            boolean result = parkingLots.isVehicleParked("vehicle1");
            Assert.assertTrue(result);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() {
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle");
            boolean result2 = parkingLots.unPark("vehicle");
            Assert.assertTrue(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToUnPark_WhenNotPresent_ShouldReturnFalse() {
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle");
            boolean result2 = parkingLots.unPark("vehicle1");
            Assert.assertFalse(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("Parking Lot is full", e.getLocalizedMessage());
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenFull_ShouldInformSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLots.register(securityStaff);
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
        } catch (ParkingLotException e) {
            boolean result = securityStaff.isCapacityFull();
            Assert.assertTrue(result);
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenHasSpace_ShouldReturnFalse() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLots.register(securityStaff);
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
        } catch (ParkingLotException e) {
            parkingLots.unPark("vehicle4");
            boolean fullCapacity = securityStaff.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenFull_ShouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLots.register(parkingLotOwner);
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
        } catch (ParkingLotException e) {
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(fullCapacity);
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenAgainHaveSpace_ShouldReturnFalse() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLots.register(parkingLotOwner);
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
        } catch (ParkingLotException e) {
            parkingLots.unPark("vehicle4");
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }

    }

    @Test
    public void givenVehicle_ShouldParkOnEmptySlot() {
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle");
            boolean result3 = parkingLots.isVehicleParked("vehicle");
            Assert.assertTrue(result3);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_shouldReturnParkedTime() {
        try {
            parkingLots.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            LocalTime time = parkingLots.getParkTime("vehicle2");
            Assert.assertEquals(LocalTime.now().withNano(0), time.withNano(0));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkedEvenly_ShouldReturnVehicleSlot() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park( DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle5");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle6");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle7");
            parkingService.unPark("vehicle5");
            Assert.assertEquals("3,1", parkingService.vehicleLocation("vehicle3"));
            Assert.assertNull(parkingService.vehicleLocation("vehicle5"));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDriver_WhenDriverIsHandicap_ShouldReturnVehicleSlot() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
            parkingService.park(DriverType.HANDICAP, Vehicle.SMALL, "vehicle5");
            parkingService.park(DriverType.HANDICAP, Vehicle.SMALL, "vehicle6");
            Assert.assertEquals("1,3", parkingService.vehicleLocation("vehicle5"));
            Assert.assertEquals("2,2", parkingService.vehicleLocation("vehicle6"));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenLargeVehicleComes_ShouldParkIn() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
            parkingService.park(DriverType.NORMAL, Vehicle.LARGE, "vehicle5");
            parkingService.park(DriverType.NORMAL, Vehicle.LARGE, "vehicle6");
            parkingService.unPark("vehicle4");
            parkingService.park(DriverType.NORMAL, Vehicle.LARGE, "vehicle7");
            Assert.assertEquals("2,2", parkingService.vehicleLocation("vehicle5"));
            Assert.assertEquals("3,2", parkingService.vehicleLocation("vehicle6"));
            Assert.assertEquals("1,2", parkingService.vehicleLocation("vehicle7"));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenNotHaveSpaceForLargeVehicle_ShouldThrowException() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle1");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle2");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle3");
            parkingService.park(DriverType.NORMAL, Vehicle.SMALL, "vehicle4");
            parkingService.park(DriverType.NORMAL, Vehicle.LARGE, "vehicle5");
            parkingService.park(DriverType.NORMAL, Vehicle.LARGE, "vehicle6");
            parkingService.unPark("vehicle4");
            parkingService.park(DriverType.NORMAL, Vehicle.LARGE, "vehicle7");
            parkingService.park(DriverType.NORMAL, Vehicle.LARGE, "vehicle8");
            Assert.assertEquals("2,2", parkingService.vehicleLocation("vehicle5"));
            Assert.assertEquals("3,2", parkingService.vehicleLocation("vehicle6"));
            Assert.assertEquals("1,2", parkingService.vehicleLocation("vehicle7"));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}