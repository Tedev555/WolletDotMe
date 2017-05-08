package com.thanongsine.wolletdotme.Activitys;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thanongsine.wolletdotme.Fragments.FragmentMain;
import com.thanongsine.wolletdotme.Fragments.FragmentMore;
import com.thanongsine.wolletdotme.Fragments.FragmentStatement;
import com.thanongsine.wolletdotme.Fragments.FragmentStatistic;
import com.thanongsine.wolletdotme.R;

public class MainActivity extends AppCompatActivity {

    String TAG = "myTag";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnLogOut;

    private ProgressDialog progressDialog;

    //RealTime database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference();

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FragmentStatement.newInstance())
                .commit();

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_item1:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, FragmentStatement.newInstance())
                                .commit();
                        break;
                    case R.id.action_item2:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, FragmentStatistic.newInstance())
                                .commit();
                        break;
                    case R.id.action_item3:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, FragmentMore.newInstance())
                                .commit();
                        break;
                }

                return false;
            }
        });



//        btnLogOut = (Button) findViewById(R.id.btn_log_out);
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    //User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//
//                }else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
//                    finish();
//                }
//
//            }
//        };

//
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut();
//            }
//        });

        //rootRef.addValueEventListener(valueEventListener);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//
//    //Sign out
//    private void signOut() {
//        mAuth.signOut();
//    }
//
//    //Data value is changed
//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//
//            User value = dataSnapshot.getValue(User.class);
//            Log.d(TAG, "Value is: " + value);
//
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            // Failed to read value
//                Log.w(TAG, "Failed to read value.", databaseError.toException());
//        }
//    };
}

//TODO : Add userProfile activity that user can manipulate user's information
