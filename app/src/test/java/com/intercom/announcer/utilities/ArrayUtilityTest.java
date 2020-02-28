package com.intercom.announcer.utilities;

import com.intercom.announcer.entities.Customer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayUtilityTest {

    @Test
    public void testSortCustomers() {
        List<Customer> customers = new ArrayList<>();

        Customer c = new Customer();
        c.setName("Abe");
        c.setUserId(5);
        customers.add(c);

        c = new Customer();
        c.setName("Don");
        c.setUserId(3);
        customers.add(c);

        c = new Customer();
        c.setName("John");
        c.setUserId(4);
        customers.add(c);

        List<Customer> sorted = ArrayUtility.sortCustomers(customers);
        long expectedFirstId = 3;
        long actualFirstId = sorted.get(0).getUserId();

        assertEquals(expectedFirstId, actualFirstId);
    }
}