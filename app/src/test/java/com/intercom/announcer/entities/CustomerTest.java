package com.intercom.announcer.entities;

import com.intercom.announcer.testutility.FileLoader;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

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

    @Test
    public void testToCustomerList() throws IOException {
        String raw = FileLoader.getStringFromFile(getClass().getClassLoader());

        List<Customer> customers = Customer.toCustomerList(raw);

        assertEquals(32, customers.size());
    }
}