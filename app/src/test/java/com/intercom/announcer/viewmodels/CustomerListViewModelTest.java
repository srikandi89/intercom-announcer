package com.intercom.announcer.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.javafaker.Faker;
import com.intercom.announcer.Config;
import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;
import com.intercom.announcer.repositories.CustomerRepository;
import com.intercom.announcer.repositories.CustomerRepositoryImpl;
import com.intercom.announcer.services.CustomerService;
import com.intercom.announcer.utilities.ArrayUtility;
import com.intercom.announcer.utilities.LocationUtility;
import com.intercom.announcer.utilities.StringUtility;
import com.jraska.livedata.TestObserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerListViewModelTest {
    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    private RestApi restApi;
    private String baseUrl;
    private String customersTest;
    private MockWebServer mockWebServer;
    private CustomerListViewModel customerListVm;
    private TestObserver<List<Customer>> customerListObserver;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        baseUrl = "http://someUrl/";
        restApi = new RestApi(baseUrl);
        restApi.build();

        InputStream is = getClass().getClassLoader().getResourceAsStream("customers.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = reader.readLine()) != null) {
            sb.append(temp+"\n");
        }

        customersTest = sb.toString();

        customerListVm = new CustomerListViewModel();
    }

    @Test
    public void testGetCustomerListLiveData() throws IOException, InterruptedException {
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(customersTest);
        mockWebServer.start();
        mockWebServer.enqueue(mockResponse);

//        CustomerService customerService = restApi.getApi().create(CustomerService.class);
//        when(restApi.getApi().create(CustomerService.class).getCustomers()).thenReturn(mockWebServer)

        customerListVm.init(restApi);

        List<Customer> expectedCustomers = new ArrayList<>();
        String[] rawList = StringUtility.parseStrings(customersTest);
        for (String data : rawList) {
            Customer customer = Customer.toCustomer(data);

            if (customer != null) {
                Location l2 = new Location(customer.getLatitude(), customer.getLongitude());

                double threshold = LocationUtility.getDistance(Config.sourceLocation, l2);
                customer.setDistance(threshold);

                if (LocationUtility.inRange(threshold, Config.distanceThreshold)) {
                    expectedCustomers.add(customer);
                }
            }
        }

        customerListObserver = TestObserver
                .test(customerListVm.getCustomerListLiveData(Config.sourceLocation, Config.distanceThreshold))
                .awaitValue()
                .assertHasValue()
                .assertValue(expectedCustomers);

    }
}