package com.sample.abhijeet.inventorymanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
