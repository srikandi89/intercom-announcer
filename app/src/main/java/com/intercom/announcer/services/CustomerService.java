package com.intercom.announcer.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CustomerService {
    @GET("intercom-take-home-test/customers.txt")
    Call<ResponseBody> getCustomers();
}
