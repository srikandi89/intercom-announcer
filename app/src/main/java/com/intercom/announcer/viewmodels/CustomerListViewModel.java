package com.intercom.announcer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.intercom.announcer.Config;
import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;
import com.intercom.announcer.repositories.CustomerRepositoryImpl;
import com.intercom.announcer.utilities.ArrayUtility;
import com.intercom.announcer.utilities.LocationUtility;
import com.intercom.announcer.utilities.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class CustomerListViewModel extends ViewModel {
    private CustomerRepositoryImpl customerRepositoryImpl;
    private LiveData<List<Customer>> customerListLiveData;

    public void init(RestApi restApi) {
        customerRepositoryImpl = new CustomerRepositoryImpl(restApi);
        customerListLiveData = new MutableLiveData<>();
    }

    /**
     * This function will be return list of customers within given distance
     * from certain location
     * @return live data of list of customer
     */
    public LiveData<List<Customer>> getCustomerListLiveData(Location source, double thresholdDistance) {
        customerListLiveData = Transformations.map(customerRepositoryImpl.getCustomersLiveData(), raw -> {
            if (raw == null) {
                return null;
            }

            List<Customer> customers = new ArrayList<>();

            List<Customer> temp = Customer.toCustomerList(raw);
            for (Customer customer: temp) {
                Location l2 = new Location(customer.getLatitude(), customer.getLongitude());
                double distance = LocationUtility.getDistance(source, l2);

                if (LocationUtility.inRange(distance, thresholdDistance)) {
                    customer.setDistance(distance);
                    customers.add(customer);
                }
            }

            return ArrayUtility.sortCustomers(customers);
        });

        return customerListLiveData;
    }
}
