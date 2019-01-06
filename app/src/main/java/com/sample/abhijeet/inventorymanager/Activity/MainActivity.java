package com.sample.abhijeet.inventorymanager.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.sample.abhijeet.inventorymanager.Data.ItemDetails;
import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.adapters.PurchaseDetailsBeanAdapter;
import com.sample.abhijeet.inventorymanager.network.NetworkUtils;
import com.sample.abhijeet.inventorymanager.util.ApplicationUtils;
import com.sample.abhijeet.inventorymanager.util.DialogUtils;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;
import com.sample.abhijeet.inventorymanager.util.RecyclerItemTouchHelper;
import com.sample.abhijeet.inventorymanager.viewModels.ItemDetailsViewModel;
import com.sample.abhijeet.inventorymanager.viewModels.PurchaseDetailsViewModel;

public class MainActivity extends AppCompatActivity implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public static final String MAIN_ACTIVITY = "MainActivity";
    private static final int RC_BARCODE_CAPTURE = 9001;
    PurchaseDetailsBeanAdapter mPurchaseDetailsAdapter = null;
    PurchaseDetailsViewModel purchaseViewModel;
    ItemDetailsViewModel itemDetailsViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        purchaseViewModel = ViewModelProviders.of(this).get(PurchaseDetailsViewModel.class);
        itemDetailsViewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView listView = findViewById(R.id.main_ListView);
        SwipeRefreshLayout swipeContainer =  findViewById(R.id.swipeContainer);


        mPurchaseDetailsAdapter = new PurchaseDetailsBeanAdapter(MainActivity.this,null);
        listView.setAdapter(mPurchaseDetailsAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(listView);

        //Listener for the main FAB button
        FloatingActionButton FAB =  findViewById(R.id.addNewItemFAB);
        FAB.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
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
        purchaseViewModel.getPurchaseList().observe(this, purchaseList -> {
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
                                                    ApplicationUtils.getInstance().showToast("Do nothing",0);
                                                    createDialogForNewPurchase("4005900311511").show();
                                                }
                                            }

        );



        FloatingActionButton mgetTestPurchaseDetails = (FloatingActionButton) findViewById(R.id.getTestPurchaseDetails);
        mgetTestPurchaseDetails.setOnClickListener((View view) -> {
                                                            String userUUID = GlobalSettings.getCurrentUserUUID();
                                                           //Toast.makeText(MainActivity.this, "Getting purchases for userUUID: " +userUUID, Toast.LENGTH_SHORT).show();
                                                           ApplicationUtils.getInstance().showSnackbar("Creating a new Purchase", findViewById( R.id.mainCoordinatorLayout));
                                                           purchaseViewModel.createPurchase("001002003004");
                                                       }

        );

        swipeContainer.setOnRefreshListener(() -> {
                purchaseViewModel.fetchAndSavePurchases();
                itemDetailsViewModel.fetchAndSaveItemDetails();
                swipeContainer.setRefreshing(false);
            }
        );

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
    private AlertDialog createDialogForNewPurchase(String detectedBarcode)
    {

        ItemDetails itemDetails =  itemDetailsViewModel.fetchItemByBarcode(detectedBarcode);
        DialogInterface.OnClickListener positiveListner = (dialog, id) -> {
            //  Action for 'NO' Button
            dialog.cancel();
            purchaseViewModel.createPurchase(detectedBarcode);
            ApplicationUtils.getInstance().showSnackbar("Saving Changes", findViewById( R.id.mainCoordinatorLayout));
        };

        DialogInterface.OnClickListener negativeListner = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //  Action for 'NO' Button
                dialog.cancel();
                Toast.makeText(getApplicationContext(),"Negative",
                        Toast.LENGTH_SHORT).show();
            }
        };
        String message = "";
        if(itemDetails != null)
            message = "Continue Purchasing " + itemDetails.getItemName() + "\nRs. " + itemDetails.getItemPrice();
        else
            message = "No details found for this item.";
        AlertDialog ad = DialogUtils.createGenericDialog(MainActivity.this,message,"Confirm Purchase",false,"Yes, perfect.","No, go Back.",positiveListner,negativeListner);
        return ad;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Toast.makeText(this, "Success, value= " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                    createDialogForNewPurchase(barcode.displayValue).show();

                    /*** Code below the dialog is never executed for some unknown reasons***/
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
       int purhcaseId =  mPurchaseDetailsAdapter.deleteItemFromList(position);
       purchaseViewModel.deletePurchase(purhcaseId);
        Toast.makeText(this, "SWIPED", Toast.LENGTH_SHORT).show();
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

}


