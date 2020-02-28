package com.intercom.announcer.repositories;

import androidx.lifecycle.MutableLiveData;

public interface CustomerRepository {
    MutableLiveData<String> getCustomersLiveData();
}
