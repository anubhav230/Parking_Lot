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
        String car = "car1";
        int result = parkingLot.park(car);
        boolean result2 = parkingLot.unPark(car);
        Assert.assertTrue(result2);
    }
}
