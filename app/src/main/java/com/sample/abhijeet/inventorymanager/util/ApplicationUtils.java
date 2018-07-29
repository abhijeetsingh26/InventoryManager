package com.sample.abhijeet.inventorymanager.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ApplicationUtils {
    private static String LOG_TAG = ApplicationUtils.class.getSimpleName();

    private static final ApplicationUtils ourInstance = new ApplicationUtils();

    public static ApplicationUtils getInstance() {
        return ourInstance;
    }

    private ApplicationUtils() {
    }


    public synchronized void showToast(String message, int toastLength)
    {
        try
        {
            Toast.makeText(MyApplication.getAppContext(), message, toastLength).show();
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG , "IF THIS HAS BEEN PRINTED SOMETHING SERIOUS IS WRONG",e);
        }
    }

    public synchronized ProgressDialog showProgressDialog(Context context)
    {

                ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Authenticating");
                progressDialog.setMessage("Connecting to Server, Please wait.");
                progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progressDialog.show();
                return progressDialog;
    }

    public void showSnackbar(String message, View view){
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
