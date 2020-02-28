package com.intercom.announcer.utilities;

import com.intercom.announcer.entities.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayUtility {
    public static List<Customer> sortCustomers(List<Customer> customers) {
        List<Customer> sorted = new ArrayList<>(customers);
        Collections.sort(sorted, (o1, o2) -> o1.getUserId()-o2.getUserId());

        return sorted;
    }
}
