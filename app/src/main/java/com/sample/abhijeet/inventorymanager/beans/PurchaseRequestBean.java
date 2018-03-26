package com.sample.abhijeet.inventorymanager.beans;

/**
 * Created by abhi2 on 3/26/2018.
 */

public class PurchaseRequestBean {
    String userUUID;
    String itemBarCode;
    public String getUserUUID() {
        return userUUID;
    }
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }
    public String getItemBarCode() {
        return itemBarCode;
    }
    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }
}
