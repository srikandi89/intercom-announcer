package com.intercom.announcer.repositories;

import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerRepositoryImplTest {

    CustomerRepositoryImpl customerRepositoryImpl;
    RestApi restApi;
    String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "someUrl";
        restApi = new RestApi(baseUrl);
        customerRepositoryImpl = new CustomerRepositoryImpl(restApi);
    }

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

        List<Customer> sorted = customerRepositoryImpl.sortCustomers(customers);
        long expectedFirstId = 3;
        long actualFirstId = sorted.get(0).getUserId();

        assertEquals(expectedFirstId, actualFirstId);
    }

    @Test
    public void testInRange() {
        assertTrue(customerRepositoryImpl.inRange(90, 100));
        assertFalse(customerRepositoryImpl.inRange(110,100));
    }
}