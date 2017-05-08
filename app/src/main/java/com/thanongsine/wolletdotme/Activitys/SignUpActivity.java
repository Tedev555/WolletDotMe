package com.thanongsine.wolletdotme.Activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thanongsine.wolletdotme.Dao.User;
import com.thanongsine.wolletdotme.R;

/**
 * Created by ted555 on 4/1/17.
 */

public class SignUpActivity extends AppCompatActivity {
    TextView emailTxt;
    TextView passwordTxt;
    TextView usernameTxt;
    Button btnSignUp;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "myTag";

    //Firebase RealTime Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference();
    DatabaseReference userRef = rootRef.child("users");

    String uId;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailTxt = (TextView) findViewById(R.id.email_txt);
        passwordTxt = (TextView) findViewById(R.id.password_txt);
        usernameTxt = (TextView) findViewById(R.id.username_txt);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);

        progressDialog = new ProgressDialog(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpAccount(emailTxt.getText().toString(), passwordTxt.getText().toString()
                        , usernameTxt.getText().toString());
            }
        });

    }

    private String getUserId() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    uId = user.getUid();
                }else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        return uId;
    }

    private void signUpAccount(final String email, String password, final String username) {

        //Show progress dialog
        progressDialog.setMessage("Processing ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                }else {

                    addNewUser(username, email);
                    Toast.makeText(SignUpActivity.this, R.string.sign_up_success,
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid= true;

        String email = emailTxt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailTxt.setError("Required.");
            valid = false;
        }else {
            emailTxt.setError(null);
        }

        String password = passwordTxt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            passwordTxt.setError("Required.");
            valid = false;
        }else {
            passwordTxt.setError(null);
        }

        String username = usernameTxt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            usernameTxt.setError("Required.");
            valid = false;
        }else {
            passwordTxt.setError(null);
        }

        return valid;
    }

    private void addNewUser(String username, String email) {

        User user = new User(username, email, "male");
        String userId = userRef.push().getKey();
        userRef.child(userId).setValue(user);
    }
}
