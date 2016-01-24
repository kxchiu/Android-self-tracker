package edu.uw.cwc8.selftracker;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MilkteaActivity extends AppCompatActivity implements MilkteaFragment.OnMilkteaSelectionListener {

    private static final String TAG = "MilkteaActivity";
    private boolean rightVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int config = getResources().getConfiguration().orientation;

        if(config == Configuration.ORIENTATION_LANDSCAPE){
            FrameLayout fRight = (FrameLayout)findViewById(R.id.container_right);
            rightVisible = fRight.getVisibility() == View.VISIBLE;
            Log.v(TAG, "" + fRight.getVisibility());
        } else {
            Log.v(TAG, "Kaboom");
        }

        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if(rightVisible){
            //ft.add(R.id.container_right, new MilkteaFragment());
        } else {
            //ft.add(R.id.container, new MilkteaFragment());
        }
        ft.commit();

        showSearchFragment();
    }


    //overide the onCreate menu method and inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    //tell the callback when menu item is selected
    //figure which item is clicked on, call the method, and return
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                showSearchFragment();
                return true;
            case R.id.action_favorites:
                showFavoritesFragment();
                return true;
            case R.id.action_test:
                runTest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //load in the fragment
    private void showSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MilkteaFragment())
                .commit();
    }

    private void showFavoritesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MilkteaFragment())
                .addToBackStack(null)
                .commit();
    }

    //a method to test something!
    private void runTest() {
        Log.v(TAG, "Test button clicked!");

        Toast.makeText(this, "Making some yummy toast", Toast.LENGTH_SHORT).show();

        MilkteaDatabase.testDatabase(this);
    }

    @Override
    public void onMilkteaSelected(Milktea milktea) {
        DetailFragment detail = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", milktea.title);
        bundle.putInt("cup", milktea.cup);
        bundle.putString("time", milktea.time);
        bundle.putString("description", milktea.desc);

        detail.setArguments(bundle);

        //swap the fragments
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detail)
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //for non-support Activity
    //    public void onBackPressed() {
    //        if(getFragmentManager().getBackStackEntryCount() != 0) {
    //            getFragmentManager().popBackStack();
    //        } else {
    //            super.onBackPressed();
    //        }
    //        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    //    }
}