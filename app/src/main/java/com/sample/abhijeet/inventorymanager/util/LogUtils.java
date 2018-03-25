package com.sample.abhijeet.inventorymanager.util;

import android.util.Log;

/**
 * Created by abhi2 on 3/25/2018.
 */

    public class LogUtils {
        public final static boolean DEBUG = true;
        public final static boolean ERROR = true;
        public final static String LOG_TAG = "IWebservice ->" ;
        public static void logDebug(String message) {
            if (DEBUG) {
                String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
                String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
                String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
                int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

                Log.d(LOG_TAG + className + "." + methodName + "():" + lineNumber, message);
            }
        }

        public static void logDebug(String message, Throwable e) {
            if (DEBUG) {
                String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
                String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
                String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
                int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

                Log.d(LOG_TAG+className + "." + methodName + "():" + lineNumber+ "( "+ message + " )"+"\n", message,e);
            }
        }

    public static void logError(String message) {
        if (ERROR) {
            String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

            Log.e(LOG_TAG + className + "." + methodName + "():" + lineNumber, message);
        }
    }

    public static void logError(String message, Throwable e) {
        if (ERROR) {
            String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

            Log.e(LOG_TAG+className + "." + methodName + "():" + lineNumber+ "( "+ message + " )"+"\n", message,e);
        }
    }
    }

