package com.intercom.announcer.repositories;

import androidx.lifecycle.MutableLiveData;

import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;

import java.util.List;

public interface CustomerRepository {
    MutableLiveData<List<Customer>> getCustomersLiveData(Location source, double thresholdDistance);
}
