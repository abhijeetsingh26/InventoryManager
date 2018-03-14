package com.sample.abhijeet.inventorymanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sample.abhijeet.inventorymanager.Activity.ItemDetailsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Listener for the main FAB button
        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.addNewItemFAB);
        FAB.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent editor_activity =  new Intent(MainActivity.this, ItemDetailsActivity.class);
                                       startActivity(editor_activity);
                                   }
                               }

        );



    }
}
