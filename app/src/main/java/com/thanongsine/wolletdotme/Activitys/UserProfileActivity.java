package com.thanongsine.wolletdotme.Activitys;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thanongsine.wolletdotme.Dao.User;
import com.thanongsine.wolletdotme.R;

public class UserProfileActivity extends AppCompatActivity {

    private EditText usernameEditTxt;
    private EditText emailEditTxt;

    //Firebase Authentication
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //Firebase RealTime Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        usernameEditTxt = (EditText) findViewById(R.id.username_txt);
        emailEditTxt = (EditText) findViewById(R.id.email_edit_txt);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User signed in
                    Log.e("SignedOrNot", "userId: " + user.getUid());
                }else {
                    //User signed out
                    Log.e("SignedOrNot", "Sign out");
                }
            }
        };

        rootRef.child("users").child("KhNG2RLcsg9M-jqUTBV").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    Log.e("CheckUser", "username: " + user.email);
                }else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
