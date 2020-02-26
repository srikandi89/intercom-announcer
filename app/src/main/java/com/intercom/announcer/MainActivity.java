package com.intercom.announcer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.viewmodels.CustomerListViewModel;

public class MainActivity extends AppCompatActivity {

    RestApi restApi;
    CustomerListViewModel customerListVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restApi = new RestApi(getString(R.string.base_url));
        restApi.build();

        customerListVm = new ViewModelProvider(this).get(CustomerListViewModel.class);
        customerListVm.init(restApi);
        customerListVm.getCustomerListLiveData(null, null).observe(this, customers -> {

            if (customers == null) {
                // TODO: Show failed to load customers data alert
                return;
            }

            if (customers.isEmpty()) {
                // TODO: Show no customers within given range alert
                return;
            }
        });
    }
}
