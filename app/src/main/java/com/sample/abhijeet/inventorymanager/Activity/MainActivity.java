package com.sample.abhijeet.inventorymanager.Activity;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.adapters.PurchaseDetailsBeanAdapter;
import com.sample.abhijeet.inventorymanager.beans.PurchaseResponseBean;
import com.sample.abhijeet.inventorymanager.network.NetworkUtils;
import com.sample.abhijeet.inventorymanager.util.ApplicationUtils;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;
import com.sample.abhijeet.inventorymanager.viewModels.PurchaseDetailsViewModel;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_ACTIVITY = "MainActivity";
    private static final int RC_BARCODE_CAPTURE = 9001;
    PurchaseDetailsBeanAdapter mPurchaseDetailsAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView listView = (RecyclerView) findViewById(R.id.main_ListView);
        SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        mPurchaseDetailsAdapter = new PurchaseDetailsBeanAdapter(MainActivity.this,null);
        listView.setAdapter(mPurchaseDetailsAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

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


        FloatingActionButton mgetTestDataFAB = findViewById(R.id.getTestDataFAB);
        mgetTestDataFAB.setOnClickListener((View view)-> {
                                                   JSONAsyncTask task = new JSONAsyncTask();
                                                   task.execute("http://localhost:8080/inventorywebservice/api/user/1");
                                                   Toast.makeText(MainActivity.this, "Test API call" , Toast.LENGTH_SHORT).show();
                                               }

        );


        final ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        PurchaseDetailsViewModel model = ViewModelProviders.of(this).get(PurchaseDetailsViewModel.class);
        model.getPurchaseList().observe(this, purchaseList -> {
            // update UI
            if(null != purchaseList)
             mPurchaseDetailsAdapter.setPurchaseList(purchaseList);
            mPurchaseDetailsAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            swipeContainer.setRefreshing(false);
            ApplicationUtils.getInstance().showToast("CALLBACK CALLED", Toast.LENGTH_SHORT);
        });

        FloatingActionButton mpostTestDataFAB = findViewById(R.id.postTestDataFAB);
        mpostTestDataFAB.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    PurchaseAsyncTask task = new PurchaseAsyncTask();
                                                    task.execute("xxxxx");
                                                }
                                            }

        );

        FloatingActionButton mgetTestPurchaseDetails = (FloatingActionButton) findViewById(R.id.getTestPurchaseDetails);
        mgetTestPurchaseDetails.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) {
                                                            String userUUID = GlobalSettings.getCurrentUserUUID();
                                                           Toast.makeText(MainActivity.this, "Getting purhcases for userUUID: " +userUUID, Toast.LENGTH_SHORT).show();
                                                           ApplicationUtils.getInstance().showSnackbar("SNACKKK", findViewById( R.id.mainCoordinatorLayout));
                                                           //Toast.makeText(MainActivity.this, "JUST A TOAST FOR NOW", Toast.LENGTH_SHORT).show();
                                                       }
                                                   }

        );

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.savepurchases();
                swipeContainer.setRefreshing(true);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);

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

    private void gotoSignInACtivity() {
        Intent signInActivityIntent = new Intent(this, SignInActivity.class);
        signInActivityIntent.putExtra("fromActivity", MAIN_ACTIVITY);
        startActivity(signInActivityIntent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Toast.makeText(this, "Success, value= " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                    PurchaseAsyncTask task = new PurchaseAsyncTask();
                    task.execute(barcode.displayValue);
                } else {
                    Toast.makeText(this, "Failed to read barcode", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to read barcode", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private class JSONAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String base_url = getApplicationContext().getResources().getString(R.string.SERVER_BASE_URL);
            String result = NetworkUtils.getJSONDataFromUrl(base_url + "/api/user/1");
            return result;
        }

        @Override
        protected void onPostExecute(String data) {
            Toast.makeText(MainActivity.this, "Data received is = " + data, Toast.LENGTH_SHORT).show();
        }
    }


    private class PurchaseAsyncTask extends AsyncTask<String, Void, PurchaseResponseBean> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Authenticating");
            progressDialog.setMessage("Connecting to Server, Please wait.");
            progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progressDialog.show();
        }

        @Override
        protected PurchaseResponseBean doInBackground(String... strings) {

            String base_url = getApplicationContext().getResources().getString(R.string.SERVER_BASE_URL);
            PurchaseResponseBean prb = NetworkUtils.purchasePost("001002003004", GlobalSettings.getCurrentUserUUID());
            return prb;
        }

        @Override
        protected void onPostExecute(PurchaseResponseBean prb) {
            progressDialog.dismiss();
            if (prb != null) {
                PurchaseDetailsViewModel model = ViewModelProviders.of(MainActivity.this).get(PurchaseDetailsViewModel.class);
               // model.refreshPurchaseDetails();
                Toast.makeText(MainActivity.this, "Purchase Data received is = " + prb.getPurchaseSerial(), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(MainActivity.this, "Purchase Data received is  empty", Toast.LENGTH_SHORT).show();
        }
    }
}


