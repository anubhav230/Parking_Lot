package com.parkinglotTest;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot = new ParkingLot();

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() {
        String car = "car1";
        int result = parkingLot.park(car);
        Assert.assertEquals(1,result);
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() {
        String carId = "car1";
        parkingLot.park(carId);
        boolean result2 = parkingLot.unPark(carId);
        Assert.assertTrue(result2);
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldReturnTrueResult() {
        String car1 = "car1";
        String car2 = "car2";
        String car3 =  "car3";
        parkingLot.park(car1, car2, car3);
        boolean result = parkingLot.isFull();
        Assert.assertTrue(result);
    }
}
