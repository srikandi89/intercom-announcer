package com.intercom.announcer.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;
import com.intercom.announcer.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerListViewModel extends ViewModel {
    private List<Customer> customerList;
    private CustomerRepository customerRepository;

    public void init(RestApi restApi) {
        customerList = new ArrayList<>();
        customerRepository = new CustomerRepository(restApi);
    }

    /**
     * This function will be return list of customers within given distance
     * from certain location
     * @return list of customer
     */
    public List<Customer> getCustomerList(Location source, Location target) {
        return customerRepository.getCustomersLiveData().getValue();
    }
}
