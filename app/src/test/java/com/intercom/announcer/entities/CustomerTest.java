package com.intercom.announcer.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void testToCustomer() {
        String raw = "{\"latitude\": \"52.986375\", \"user_id\": 12, \"name\": \"Christina McArdle\", \"longitude\": \"-6.043701\"}";

        Customer expected = new Customer();
        expected.setUserId(12);
        expected.setName("Christina McArdle");
        expected.setLatitude(52.986375);
        expected.setLongitude(-6.043701);

        Customer actual = Customer.toCustomer(raw);

        assertTrue(expected.getLatitude() == actual.getLatitude());
        assertTrue(expected.getLongitude() == actual.getLongitude());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getName(), actual.getName());
    }
}