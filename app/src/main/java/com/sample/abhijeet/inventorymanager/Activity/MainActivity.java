package com.sample.abhijeet.inventorymanager.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.beans.PurchaseResponseBean;
import com.sample.abhijeet.inventorymanager.network.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_ACTIVITY = "MainActivity";
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String APP_DATA_PREFERECES = "appDataPrefrences";
    private static final String APP_DATA_PREFERENCES_USER_UUID = "userUUID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Listener for the main FAB button
        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.addNewItemFAB);
        FAB.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
                                       intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                                       intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

                                       startActivityForResult(intent, RC_BARCODE_CAPTURE);
                                   }
                               }

        );


        FloatingActionButton mgetTestDataFAB = (FloatingActionButton) findViewById(R.id.getTestDataFAB);
        mgetTestDataFAB.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       JSONAsyncTask task = new JSONAsyncTask();
                                       task.execute("http://localhost:8080/inventorywebservice/api/user/1");

                                   }
                               }

        );

        FloatingActionButton mpostTestDataFAB = (FloatingActionButton) findViewById(R.id.postTestDataFAB);
        mpostTestDataFAB.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   PurchaseAsyncTask task = new PurchaseAsyncTask();
                                                   task.execute("xxxxx");
                                               }
                                           }

        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_in_overflow_menu:
                gotoSignInACtivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoSignInACtivity()
    {
        Intent signInActivityIntent = new Intent(this, SignInActivity.class);
        signInActivityIntent.putExtra("fromActivity", MAIN_ACTIVITY);
        startActivity(signInActivityIntent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE)
        {
            if (resultCode == CommonStatusCodes.SUCCESS)
            {
                if (data != null)
                {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Toast.makeText(this,"Success, value= "  + barcode.displayValue, Toast.LENGTH_SHORT).show();
                    PurchaseAsyncTask task = new PurchaseAsyncTask();
                    task.execute(barcode.displayValue);
                }
                else
                {
                    Toast.makeText(this,"Failed to read barcode", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this,"Failed to read barcode", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class JSONAsyncTask extends AsyncTask<String,Void,String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {
            String base_url = getApplicationContext().getResources().getString(R.string.SERVER_BASE_URL);
            String result = NetworkUtils.getJSONDataFromUrl(base_url+"/api/user/1");
            return result;
        }

        @Override
        protected void onPostExecute(String data)
        {
            Toast.makeText(MainActivity.this, "Data received is = " + data, Toast.LENGTH_SHORT).show();
        }
    }




    private class PurchaseAsyncTask extends AsyncTask<String,Void,PurchaseResponseBean> {

        private ProgressDialog progressDialog;

        @Override
        protected PurchaseResponseBean doInBackground(String... strings) {
            String base_url = getApplicationContext().getResources().getString(R.string.SERVER_BASE_URL);
            PurchaseResponseBean prb = NetworkUtils.purchasePost("001-002-003-004","5326188d-653d-4173-be8e-d8f83486d0ad");
            return prb;
        }

        @Override
        protected void onPostExecute(PurchaseResponseBean prb)
        {
            if(prb != null) {
                Toast.makeText(MainActivity.this, "Purchase Data received is = " + prb.getPurchaseSerial(), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MainActivity.this, "Purchase Data received is  empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        SharedPreferences myPrefs = this.getSharedPreferences(APP_DATA_PREFERECES, MODE_PRIVATE);
        myPrefs.edit().remove(APP_DATA_PREFERENCES_USER_UUID);
        myPrefs.edit().clear();
        myPrefs.edit().commit();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        SharedPreferences myPrefs = this.getSharedPreferences(APP_DATA_PREFERECES,MODE_PRIVATE);
        myPrefs.edit().remove(APP_DATA_PREFERENCES_USER_UUID);
        myPrefs.edit().clear();
        myPrefs.edit().commit();
    }
}


