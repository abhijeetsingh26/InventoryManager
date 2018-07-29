package com.sample.abhijeet.inventorymanager.modules;

import com.sample.abhijeet.inventorymanager.network.PurchaseRepositoryImpl;
import com.sample.abhijeet.inventorymanager.viewModels.LoginViewModel;
import com.sample.abhijeet.inventorymanager.viewModels.PurchaseDetailsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent
{
    void inject(PurchaseRepositoryImpl repositoryImpl);
    void inject(PurchaseDetailsViewModel purchaseDetailsViewModel);
    void inject(LoginViewModel loginViewModel);
}
