package com.sample.abhijeet.inventorymanager.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.modules.ApplicationModule;
import com.sample.abhijeet.inventorymanager.modules.DaggerApplicationComponent;
import com.sample.abhijeet.inventorymanager.network.LoginRepository;
import com.sample.abhijeet.inventorymanager.util.ApplicationUtils;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    @Inject
    LoginRepository loginRepository;

    String userUUID = GlobalSettings.getCurrentUserUUID();

    //TODO: Remove field injection and implement a constructor injection
    {
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(new MyApplication())).build().inject(this);
    }

    public MutableLiveData<LoginResponseBean> doLogin(String idToken)
    {
        ApplicationUtils.getInstance().showToast("Authenticating with server", Toast.LENGTH_SHORT);
        return loginRepository.doLoginUser(idToken);
    }
}
