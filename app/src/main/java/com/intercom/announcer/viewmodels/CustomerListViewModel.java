package com.intercom.announcer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;
import com.intercom.announcer.repositories.CustomerRepositoryImpl;

import java.util.List;

public class CustomerListViewModel extends ViewModel {
    private CustomerRepositoryImpl customerRepositoryImpl;

    public void init(RestApi restApi) {
        customerRepositoryImpl = new CustomerRepositoryImpl(restApi);
    }

    /**
     * This function will be return list of customers within given distance
     * from certain location
     * @return live data of list of customer
     */
    public LiveData<List<Customer>> getCustomerListLiveData(Location source, double distance) {
        return customerRepositoryImpl.getCustomersLiveData(source, distance);
    }
}
