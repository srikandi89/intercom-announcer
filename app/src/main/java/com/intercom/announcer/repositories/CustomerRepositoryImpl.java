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
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepositoryImpl implements CustomerRepository {
    private RestApi restApi;
    private MutableLiveData<String> rawResponseLiveData;

    public CustomerRepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
        rawResponseLiveData = new MutableLiveData<>();
    }

    @Override
    public MutableLiveData<String> getCustomersLiveData() {
        Call<ResponseBody> call = restApi.getApi().create(CustomerService.class).getCustomers();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String raw = response.body().string();
                    rawResponseLiveData.setValue(raw);
                } catch (IOException e) {
                    rawResponseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                rawResponseLiveData.setValue(null);
            }
        });
        return rawResponseLiveData;
    }

}
