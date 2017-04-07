package com.thanongsine.wolletdotme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "myTag";

    EditText emailTxt;
    EditText passTxt;
    Button btnLogIn;
    TextView signUpTxt;
    TextView forgotTxt;

    private ProgressDialog progressDialog;

    //// TODO: 4/3/17 1. Progressbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailTxt = (EditText) findViewById(R.id.email_txt);
        passTxt = (EditText) findViewById(R.id.password_txt);
        btnLogIn = (Button) findViewById(R.id.btn_sign_up);
        signUpTxt = (TextView) findViewById(R.id.sing_up_txt);
        forgotTxt = (TextView) findViewById(R.id.forgot_txt);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        //ProgressDialog
        progressDialog = new ProgressDialog(this);

        //When click Login
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Show progress dialog
                progressDialog.setMessage("Processing ");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                //progressDialog.setIndeterminate(true);
                progressDialog.show();

                final String email = emailTxt.getText().toString();
                final String password = passTxt.getText().toString();
                signIn(email, password);
            }
        });

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        forgotTxt.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Hide progress dialog
                        progressDialog.dismiss();
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SignInActivity.this, "Login Success",
                                    Toast.LENGTH_SHORT).show();

                            //Get user information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String email = user.getEmail();
                                String uId = user.getUid();
                                Log.e(TAG, "user : " + uId);
                                Log.e(TAG, "email : " + email);
                            }

                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailTxt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailTxt.setError("Required.");
            valid = false;
        } else {
            emailTxt.setError(null);
        }

        String password = passTxt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            passTxt.setError("Required.");
            valid = false;
        } else {
            passTxt.setError(null);
        }

        return valid;
    }

    private void sendEmailVerification() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_txt:
                startActivity(new Intent(SignInActivity.this, ForgotActivity.class));
        }
    }
}
