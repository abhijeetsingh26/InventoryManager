package com.sample.abhijeet.inventorymanager.util;

public class GlobalSettings {

  //  private static String currentUserUUID = "94941b89-fff6-44ab-a53e-037542a7d4a6";
  private static String sCurrentUserUUID = "" ;

    public  static String getCurrentUserUUID() {
        return sCurrentUserUUID;
    }

    public static void setCurrentUserUUID(String currentUserUUID) {
        sCurrentUserUUID = currentUserUUID;
    }
}
