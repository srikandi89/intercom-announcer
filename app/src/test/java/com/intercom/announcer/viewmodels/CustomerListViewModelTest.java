package com.intercom.announcer.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.github.javafaker.Faker;
import com.intercom.announcer.Config;
import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.entities.Location;
import com.intercom.announcer.repositories.CustomerRepository;
import com.intercom.announcer.repositories.CustomerRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerListViewModelTest {

    private CustomerRepository customerRepository;
    private RestApi restApi;
    private String baseUrl;
    private List<Customer> fakedCustomerList;
    private Faker faker;
    private CustomerListViewModel viewModel;
    @Mock
    Observer<List<Customer>> observer;
    private Location source = Config.sourceLocation;
    private double distanceThreshold = Config.distanceThreshold;

    @Before
    public void setUp() {
        baseUrl = "someUrl";
        restApi = new RestApi(baseUrl);
        customerRepository = new CustomerRepositoryImpl(restApi);
        fakedCustomerList = new ArrayList<>();
        faker = new Faker();
        viewModel = new CustomerListViewModel();
        viewModel.init(restApi);
        viewModel.getCustomerListLiveData(source, distanceThreshold).observeForever(observer);

        // generate fake customer list which satisfy the range condition <= 100km
        for (int i=0; i<10; i++) {
            Customer customer = new Customer();
            customer.setDistance(faker.random().nextDouble()%101);
            customer.setUserId(i+1);
            customer.setName(faker.name().name());
            fakedCustomerList.add(customer);
        }
    }

    @Test
    public void testGetCustomerListLiveData() {

        MutableLiveData<List<Customer>> mockedMutableLiveData = new MutableLiveData<>();
        mockedMutableLiveData.setValue(fakedCustomerList);

        when(customerRepository.getCustomersLiveData(source, distanceThreshold)).thenReturn(mockedMutableLiveData);
    }
}