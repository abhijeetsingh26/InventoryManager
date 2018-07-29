package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.MediatorLiveData;

import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;

public interface LoginRepository {
    MediatorLiveData<LoginResponseBean> doLoginUser(String idToken);
}
