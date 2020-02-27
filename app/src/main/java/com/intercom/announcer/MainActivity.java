package com.intercom.announcer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.intercom.announcer.adapters.CustomerListAdapter;
import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.viewmodels.CustomerListViewModel;

public class MainActivity extends AppCompatActivity {

    RestApi restApi;
    CustomerListViewModel customerListVm;
    CustomerListAdapter customerListAdapter;

    RecyclerView rvCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restApi = new RestApi(getString(R.string.base_url));
        restApi.build();

        customerListVm = new ViewModelProvider(this).get(CustomerListViewModel.class);
        customerListVm.init(restApi);

        rvCustomers = findViewById(R.id.rv_customers);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rvCustomers.setLayoutManager(llm);

        customerListAdapter = new CustomerListAdapter(this);
        rvCustomers.setAdapter(customerListAdapter);

        populateCustomerList();
    }

    private void populateCustomerList() {
        customerListVm.getCustomerListLiveData(Config.sourceLocation, Config.distanceThreshold).observe(this, customers -> {

            if (customers == null) {
                // TODO: Show failed to load customers data alert
                return;
            }

            if (customers.isEmpty()) {
                // TODO: Show no customers within given range alert
                return;
            }

            customerListAdapter.updateList(customers);
            Log.d("MAIN_ACTIVITY", "Customers: "+customers.size());
        });
    }
}
