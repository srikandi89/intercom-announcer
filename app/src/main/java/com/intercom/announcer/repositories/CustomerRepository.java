package com.intercom.announcer.repositories;

import androidx.lifecycle.MutableLiveData;

import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;
import com.intercom.announcer.services.CustomerService;
import com.intercom.announcer.utilities.LocationUtility;
import com.intercom.announcer.utilities.StringUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository {
    private RestApi restApi;

    public CustomerRepository(RestApi restApi) {
        this.restApi = restApi;
    }

    public MutableLiveData<List<Customer>> getCustomersLiveData(Location source, double distance) {

        MutableLiveData<List<Customer>> customersLiveData = new MutableLiveData<>();

        Call<ResponseBody> call = restApi.getApi().create(CustomerService.class).getCustomers();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String raw = response.body().string();
                    String[] rawList = StringUtility.parseStrings(raw);
                    List<Customer> customers = new ArrayList<>();

                    if (rawList != null) {
                        for (String data: rawList) {
                            Customer customer = Customer.toCustomer(data);

                            if (customer != null) {
                                Location l2 = new Location(customer.getLatitude(), customer.getLongitude());

                                if (inRange(source, l2, distance)) {
                                    customers.add(customer);
                                }
                            }
                        }

                        customersLiveData.setValue(customers);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customersLiveData.setValue(null);
            }
        });

        return customersLiveData;
    }

    public boolean inRange(Location l1, Location l2, double distance) {
        return LocationUtility.getDistance(l1, l2) <= distance;
    }

    public List<Customer> sortCustomers(List<Customer> customers) {
        List<Customer> sorted = new ArrayList<>(customers);
        Collections.sort(sorted, (o1, o2) -> o1.getUserId()-o2.getUserId());

        return sorted;
    }
}
