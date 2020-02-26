package com.intercom.announcer.repositories;

import androidx.lifecycle.MutableLiveData;

import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.services.CustomerService;
import com.intercom.announcer.utilities.StringUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository {
    private RestApi restApi;
    private MutableLiveData<List<Customer>> customersLiveData;

    public CustomerRepository(RestApi restApi) {
        this.restApi = restApi;
        customersLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Customer>> getCustomersLiveData() {

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
                            customers.add(customer);
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
}
