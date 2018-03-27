package com.sample.abhijeet.inventorymanager.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.network.NetworkUtils;

/**
 * Activity to demonstrate basic retrieval of the Google user's ID, email address, and basic
 * profile.
 */
public class SignInActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mTitleText;
    private Button mProceedButton;
    private static  boolean isUserSignedIN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Views
        mStatusTextView = findViewById(R.id.status);
        mTitleText =  findViewById(R.id.title_text);
        mProceedButton =  findViewById(R.id.button_proceed);
        // Do not need the proceed button until the user is signed in
        mProceedButton.setVisibility(View.INVISIBLE);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
        mProceedButton.setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getResources().getString(R.string.SERVER_CLIENT_ID))
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        // [END customize_button]
    }

    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        // [END on_start_sign_in]
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.APP_DATA_PREFERECES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(MainActivity.APP_DATA_PREFERENCES_USER_UUID, null);
                        editor.commit();
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.APP_DATA_PREFERECES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(MainActivity.APP_DATA_PREFERENCES_USER_UUID, null);
                        editor.commit();
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            // Successfull Sign-In
            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
            mTitleText.setText(R.string.title_text_successfull_sign_in);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            mProceedButton.setVisibility(View.VISIBLE);
            isUserSignedIN = true;
            String token = account.getIdToken();
           // Toast.makeText(this, "Token=" + token, Toast.LENGTH_SHORT).show();
            SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.APP_DATA_PREFERECES, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            String userUUID =  pref.getString(MainActivity.APP_DATA_PREFERENCES_USER_UUID, null);
            if(userUUID == null) {
                LoginAsyncTask task = new LoginAsyncTask();
                task.execute(token);
            }
            else {
                proceedForCondition();
            }
        } else {
            // Unsuccessfull Sign-In
            mStatusTextView.setText(R.string.signed_out);
            mTitleText.setText(R.string.title_text_not_signed_in);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            mProceedButton.setVisibility(View.INVISIBLE);
            isUserSignedIN = false;
        }
    }
     private void proceedForCondition()
    {
        String fromActivity = getIntent().getStringExtra("fromActivity");
        if(fromActivity !=null && fromActivity.equals(MainActivity.MAIN_ACTIVITY))
        {
            //Do nothing, stay on this page if it was a EXPLICIT request

        }
        else
        {
            // Else, proceed and do not show the screen if User has already Signed-In
            proceed();
        }
    }

    private void proceed()
    {
      Intent mainActivityIntent =  new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
            case R.id.button_proceed:
                proceed();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        // User must be signed In to use this activity, check it before giving access
        if(isUserSignedIN)
            super.onBackPressed();
        else
            Toast.makeText(this, R.string.ask_user_sign_in, Toast.LENGTH_SHORT).show();

    }

    private class LoginAsyncTask extends AsyncTask<String, Void, LoginResponseBean> {

       private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SignInActivity.this);
            progressDialog.setTitle("Connecting to Server");
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progressDialog.show();
        }

        @Override
        protected LoginResponseBean doInBackground(String...  token) {

            String base_url = getApplicationContext().getResources().getString(R.string.SERVER_BASE_URL);
              LoginResponseBean lrb =  NetworkUtils.LoginPost(token[0]);
            return lrb;
        }

        @Override
        protected void onPostExecute(LoginResponseBean lrb) {
            progressDialog.dismiss();
            if (lrb != null) {
                Toast.makeText(SignInActivity.this, "Logged-In as  " + lrb.getUserFname(), Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.APP_DATA_PREFERECES, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(MainActivity.APP_DATA_PREFERENCES_USER_UUID, lrb.getUserUUID());
                editor.commit();

                Intent mainActivityIntent =  new Intent(SignInActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);

            } else {
                Toast.makeText(SignInActivity.this, "No Response From Server !", Toast.LENGTH_SHORT).show();
            }



        }
    }

}
