package com.sample.abhijeet.inventorymanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.abhijeet.inventorymanager.Data.Purchase;
import com.sample.abhijeet.inventorymanager.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PurchaseDetailsBeanAdapter extends RecyclerView.Adapter<PurchaseDetailsBeanAdapter.ViewHolder> {
    private String LOG_TAG = "PurchaseDetailsBeanAdapter";
    List <Purchase> purchaseList = Collections.emptyList();
    private LayoutInflater inflater;


    public PurchaseDetailsBeanAdapter(Context ctx, Purchase[] data) {
        inflater = LayoutInflater.from(ctx);

        List <Purchase> purchaseList = new ArrayList<>();
        if(data != null) {
            for (Purchase currentBean : data) {
                purchaseList.add(currentBean);
            }
        }
        this.purchaseList = purchaseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(PurchaseDetailsBeanAdapter.ViewHolder holder, int position) {
    Purchase purchase = purchaseList.get(position);
    holder.itemNameTextView.setText(purchase.getItemName());
    holder.itemDateTextView.setText(purchase.getCreatedAt().toString());
    holder.itemPriceTextView.setText(String.valueOf(purchase.getItemPrice()));
    }

    public int deleteItemFromList(int position)
    {
        Purchase purchaseToDelete = purchaseList.get(position);
        purchaseList.remove(position);
        notifyItemRemoved(position);
        return purchaseToDelete.getPurchaseSerial();
    }

    public void addItemToList(int position, Purchase purchase)
    {
        purchaseList.add(position,purchase);
        notifyItemInserted(position);
    }

    public Purchase getItemAtPosition(int position)
    {
        Purchase purchase = purchaseList.get(position);
        return purchase;
    }
    public void setPurchaseList(Purchase[] purchaseListIn)
    {
        List <Purchase> purchaseList = new ArrayList<>();
        for (Purchase currentBean:purchaseListIn)
        {
            purchaseList.add(currentBean);
        }
            this.purchaseList = purchaseList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemDateTextView;
        public RelativeLayout viewBackground, viewForeground;

        ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_details_listitem, parent, false));
            itemNameTextView = (TextView) itemView.findViewById(R.id.purchaseDetailsList_itemName);
            itemPriceTextView = (TextView) itemView.findViewById(R.id.purchaseDetailsList_itemPrice);
            itemDateTextView = (TextView) itemView.findViewById(R.id.purchaseDetailsList_itemDate);
        }
    }
}
