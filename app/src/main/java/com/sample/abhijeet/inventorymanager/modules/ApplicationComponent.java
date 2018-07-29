package com.sample.abhijeet.inventorymanager.modules;

import com.sample.abhijeet.inventorymanager.network.RepositoryImpl;
import com.sample.abhijeet.inventorymanager.viewModels.PurchaseDetailsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent
{
    void inject(RepositoryImpl repositoryImpl);
    void inject(PurchaseDetailsViewModel purchaseDetailsViewModel);
}
