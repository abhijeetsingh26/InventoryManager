package com.sample.abhijeet.inventorymanager.beans;

import java.io.Serializable;

/**
 * Created by abhi2 on 3/26/2018.
 */

public class PurchaseResponseBean implements Serializable {
    String itemName;

    String message;

    int purchaseSerial;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPurchaseSerial() {
        return purchaseSerial;
    }

    public void setPurchaseSerial(int purchaseSerial) {
        this.purchaseSerial = purchaseSerial;
    }

    public Boolean getisPurchaseSuccessfull() {
        return isPurchaseSuccessfull;
    }

    public void setisPurchaseSuccessfull(Boolean purchaseSuccessfull) {
        isPurchaseSuccessfull = purchaseSuccessfull;
    }

    Boolean isPurchaseSuccessfull;

}
