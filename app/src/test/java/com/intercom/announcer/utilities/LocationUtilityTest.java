package com.intercom.announcer.utilities;

import com.intercom.announcer.entities.Location;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationUtilityTest {

    /**
     * The idea for the double / floating point test that used is,
     * subtract the actual and expected one.
     * If the absolute result is less than threshold, then the assertion is true
     */
    @Test
    public void testGetDistance() {
        Location l1 = new Location(47.6788206, -122.3271205);
        Location l2 = new Location(47.6788206, -122.5271205);

        double actual = LocationUtility.getDistance(l1, l2);
        double expected = 14.973190481586224;
        double threshold = 0.001;

        assertTrue(Math.abs(actual - expected) < threshold);
    }

    @Test
    public void testInRange() {
        assertTrue(LocationUtility.inRange(90, 100));
        assertFalse(LocationUtility.inRange(110,100));
    }
}