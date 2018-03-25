package com.sample.abhijeet.inventorymanager.beans;

/**
 * Created by abhi2 on 3/25/2018.
 */

public class LoginResponseBean {

    public String userUUID;

    public String userFname;

    public String userLname;

    public Boolean isloginVerified;

    public String message="";

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public Boolean getIsloginVerified() {
        return isloginVerified;
    }

    public void setIsloginVerified(Boolean isloginVerified) {
        this.isloginVerified = isloginVerified;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
