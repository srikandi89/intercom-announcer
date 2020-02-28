package com.intercom.announcer.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.intercom.announcer.Config;
import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;
import com.intercom.announcer.testutility.FileLoader;
import com.intercom.announcer.utilities.ArrayUtility;
import com.intercom.announcer.utilities.LocationUtility;
import com.intercom.announcer.utilities.StringUtility;
import com.jraska.livedata.TestObserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

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
        mockWebServer.start();
        baseUrl = mockWebServer.url("/").toString();
        restApi = new RestApi(baseUrl);
        restApi.build();

        customersTest = FileLoader.getStringFromFile(getClass().getClassLoader());

        customerListVm = new CustomerListViewModel();
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetCustomerListLiveData_response200() throws InterruptedException {
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(customersTest);
        mockWebServer.enqueue(mockResponse);

        customerListVm.init(restApi);

        List<Customer> expectedCustomers = new ArrayList<>();

        List<Customer> temp = Customer.toCustomerList(customersTest);

        for (Customer customer: temp) {
            Location l2 = new Location(customer.getLatitude(), customer.getLongitude());
            double distance = LocationUtility.getDistance(Config.sourceLocation, l2);

            if (LocationUtility.inRange(distance, Config.distanceThreshold)) {
                customer.setDistance(distance);
                expectedCustomers.add(customer);
            }
        }

        expectedCustomers = ArrayUtility.sortCustomers(expectedCustomers);

        customerListObserver = TestObserver
                .test(customerListVm.getCustomerListLiveData(Config.sourceLocation, Config.distanceThreshold))
                .awaitValue();

        customerListObserver.assertHasValue();


        for (int i=0; i<expectedCustomers.size(); i++) {
            assertEquals(expectedCustomers.get(i).getName(), customerListObserver.value().get(i).getName());
            assertTrue(Math.abs(expectedCustomers.get(i).getDistance() - customerListObserver.value().get(i).getDistance()) <= 0.001);
        }
    }

    @Test
    public void testGetCustomerListLiveData_responseNot2xx() throws InterruptedException {
        MockResponse mockResponse = new MockResponse().setResponseCode(401).setBody("");
        mockWebServer.enqueue(mockResponse);

        customerListVm.init(restApi);

        customerListObserver = TestObserver
                .test(customerListVm.getCustomerListLiveData(Config.sourceLocation, Config.distanceThreshold))
                .awaitValue();

        customerListObserver.assertHasValue();

        List<Customer> expectedCustomers = null;

        assertEquals(expectedCustomers, customerListObserver.value());
    }
}