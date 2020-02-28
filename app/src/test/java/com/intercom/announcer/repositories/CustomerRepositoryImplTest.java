package com.intercom.announcer.repositories;

import com.intercom.announcer.core.RestApi;
import com.intercom.announcer.entities.Customer;
import com.intercom.announcer.utilities.ArrayUtility;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerRepositoryImplTest {

    CustomerRepositoryImpl customerRepositoryImpl;
    RestApi restApi;
    String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "someUrl";
        restApi = new RestApi(baseUrl);
        customerRepositoryImpl = new CustomerRepositoryImpl(restApi);
    }



}