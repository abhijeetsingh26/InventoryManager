package com.sample.abhijeet.inventorymanager.util;



import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtils
{
    public static AlertDialog createGenericDialog(Context context, String dialog_message, String dialog_title, Boolean isCancelable, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener)
    {
         AlertDialog.Builder builder = new AlertDialog.Builder(context);
         builder.setMessage(dialog_message) .setTitle(dialog_title);
         builder.setCancelable(isCancelable);
         builder.setPositiveButton(positiveButtonText,positiveListener);
        builder.setNegativeButton(negativeButtonText,negativeListener);
        AlertDialog alert = builder.create();
        return alert;
    }
}
