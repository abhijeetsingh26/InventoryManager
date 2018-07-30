package com.sample.abhijeet.inventorymanager.modules;

import android.app.Application;

import com.sample.abhijeet.inventorymanager.Data.Database;
import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.network.ItemDetailsRepository;
import com.sample.abhijeet.inventorymanager.network.ItemDetailsRepositoryImpl;
import com.sample.abhijeet.inventorymanager.network.LoginRepository;
import com.sample.abhijeet.inventorymanager.network.LoginRepositoryImpl;
import com.sample.abhijeet.inventorymanager.network.PurchaseRepository;
import com.sample.abhijeet.inventorymanager.network.PurchaseRepositoryImpl;
import com.sample.abhijeet.inventorymanager.network.Webservice;
import com.sample.abhijeet.inventorymanager.util.AppExecutors;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApplicationModule
{
    private Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    /*@Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit()
    {

        return new Retrofit.Builder().baseUrl(mApplication.getResources().getString(R.string.SERVER_BASE_URL)).addConverterFactory(JacksonConverterFactory.create()).build();
    }

    @Singleton
    @Provides
    Webservice providesWebService()
    {
        return providesRetrofit().create(Webservice.class);
    }
    */

    @Singleton
    @Provides
    Retrofit providesRetrofit()
    {
        return new Retrofit.Builder().baseUrl(MyApplication.getAppContext().getResources().getString(R.string.SERVER_BASE_URL)) .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    @Singleton
    @Provides
    Webservice providesWebservice()
    {
        return(providesRetrofit().create(Webservice.class));
    }

    @Singleton
    @Provides
    PurchaseRepository providesPurchaseRepository()
    {

        return new PurchaseRepositoryImpl( providesWebservice(), providesAppExecutors(), providesDatabase());
    }

    @Singleton
    @Provides
    AppExecutors providesAppExecutors()
    {
        return new AppExecutors();
    }

    @Singleton
    @Provides
    Database providesDatabase()
    {
        return Database.getInstance();
    }

    @Singleton
    @Provides
    LoginRepository providesLoginRepository()
    {
        return new LoginRepositoryImpl( providesWebservice(), providesAppExecutors(), providesDatabase());
    }

    @Singleton
    @Provides
    ItemDetailsRepository providesItemDetailsRepository()
    {
        return new ItemDetailsRepositoryImpl( providesWebservice(), providesAppExecutors(), providesDatabase());
    }

}
