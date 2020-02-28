package com.intercom.announcer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.intercom.announcer.adapters.CustomerListAdapter;
import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.viewmodels.CustomerListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RestApi restApi;
    CustomerListViewModel customerListVm;
    CustomerListAdapter customerListAdapter;

    RecyclerView rvCustomers;
    View loaderWrapper;

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

        loaderWrapper = findViewById(R.id.loader_wrapper);

        populateCustomerList();
    }

    private void populateCustomerList() {
        loaderWrapper.setVisibility(View.VISIBLE);
        rvCustomers.setVisibility(View.GONE);
        customerListVm.getCustomerListLiveData(Config.sourceLocation, Config.distanceThreshold).observe(this, this::handleCustomerListView);
    }

    private void showFailedLoadCustomer() {
        Toast.makeText(this, getString(R.string.failed_load_customer_message), Toast.LENGTH_SHORT).show();
    }

    public void handleCustomerListView(List<Customer> customers) {
        if (customers == null) {
            showFailedLoadCustomer();
            return;
        }

        if (customers.isEmpty()) {
            showFailedLoadCustomer();
            return;
        }

        loaderWrapper.setVisibility(View.GONE);
        rvCustomers.setVisibility(View.VISIBLE);
        customerListAdapter.updateList(customers);
    }
}
