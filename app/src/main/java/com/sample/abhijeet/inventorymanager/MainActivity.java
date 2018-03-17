package com.sample.abhijeet.inventorymanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.sample.abhijeet.inventorymanager.Activity.BarcodeCaptureActivity;
import com.sample.abhijeet.inventorymanager.Activity.ItemDetailsActivity;

public class MainActivity extends AppCompatActivity {
    private static final int RC_BARCODE_CAPTURE = 9001;

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
}

