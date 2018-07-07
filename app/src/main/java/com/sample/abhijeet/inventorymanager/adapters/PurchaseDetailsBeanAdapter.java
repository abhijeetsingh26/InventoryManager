package com.sample.abhijeet.inventorymanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.beans.PurchaseDetailResponseBean;

import java.util.Date;

public class PurchaseDetailsBeanAdapter extends ArrayAdapter<PurchaseDetailResponseBean> {
    private String LOG_TAG = "PurchaseDetailsBeanAdapter";
    PurchaseDetailResponseBean[] pdrbArray = null;

    public PurchaseDetailsBeanAdapter(@NonNull Context context, int resource, @NonNull PurchaseDetailResponseBean[] objects) {
        super(context, resource, objects);
        pdrbArray = objects;
    }
    @Override
    public int getCount() {
        return pdrbArray.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.purchase_details_listitem, null);
        TextView itemNameTextView = (TextView) v.findViewById(R.id.purchaseDetailsList_itemName);
        TextView itemPriceTextView = (TextView) v.findViewById(R.id.purchaseDetailsList_itemPrice);
        TextView itemDateTextView = (TextView) v.findViewById(R.id.purchaseDetailsList_itemDate);

//        Log.e(LOG_TAG, "getView: ["+ pdrbArray+"]" );
//        Log.e(LOG_TAG, "getView:position ["+ position+"]" );
//        Log.e(LOG_TAG, "getView:itemNameTextView ["+ itemNameTextView+"]" );
//        Log.e(LOG_TAG, "getView:itemPriceTextView ["+ itemPriceTextView+"]" );
//        Log.e(LOG_TAG, "pdrbArray[position].getItemName() ["+ pdrbArray[position].getItemName()+"]" );
//        Log.e(LOG_TAG, "pdrbArray[position].getItemPrice() ["+ pdrbArray[position].getItemPrice()+"]" );
        String itemName = pdrbArray[position].getItemName();
        String itemPrice = pdrbArray[position].getItemPrice() + "";
        Date itemDate = pdrbArray[position].getModifiedAt();
        String itemDateString =  itemDate.toString();
        itemNameTextView.setText(itemName);
        itemPriceTextView.setText(itemPrice);
        itemDateTextView.setText(itemDateString);
        return v;
    }
}
