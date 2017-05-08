package com.thanongsine.wolletdotme.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.thanongsine.wolletdotme.Fragments.FragmentAddIncome;
import com.thanongsine.wolletdotme.Fragments.FragmentStatement;
import com.thanongsine.wolletdotme.R;

public class AddStatementActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statement);

        //Setup toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//Create home bottom
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//Set Title
        getSupportActionBar().setTitle("Add Income");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FragmentAddIncome.newInstance())
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
}
