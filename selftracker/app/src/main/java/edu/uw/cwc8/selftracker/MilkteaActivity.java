package edu.uw.cwc8.selftracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MilkteaActivity extends AppCompatActivity implements MilkteaFragment.OnMilkteaSelectionListener {

    private static final String TAG = "MilkteaActivity";
    private boolean rightVisible;
    public FrameLayout frameLeft;
    public FrameLayout frameRight;
    public static final String MASTER_FRAG = "MasterFrag";
    public static final String SUMMARY_FRAG = "SummaryFrag";
    public static final String DETAIL_FRAG = "DetailFrag";
    private int config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = getResources().getConfiguration().orientation;

        frameLeft = (FrameLayout)findViewById(R.id.containerLeft);
        frameRight = (FrameLayout)findViewById(R.id.containerRight);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if(config == Configuration.ORIENTATION_LANDSCAPE){
            FrameLayout fRight = (FrameLayout)findViewById(R.id.containerRight);
            rightVisible = fRight.getVisibility() == View.VISIBLE;
            Log.v(TAG, "LandScape Mode Visibility: " + fRight.getVisibility());

            showMilkteaFragment();
            showDetailFragment();

        } else {
            Log.v(TAG, "Kaboom!! You are not in landscape mode!");
        }
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
                showMilkteaFragment();
                return true;
            case R.id.action_summary:
                showSummaryFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //load in the milktea fragment
    private void showMilkteaFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLeft, new MilkteaFragment())
                .commit();
    }

    //load in the detail fragment
    private void showDetailFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerRight, new DetailFragment())
                .addToBackStack(null)
                .commit();
    }

    //load in the summary fragment
    private void showSummaryFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerRight, new SummaryFragment())
                .addToBackStack(null)
                .commit();
    }

    public void onShowDialog(AddFragment fragment) {
        Log.v(TAG, "Callback active");
        FragmentManager manager = getFragmentManager();
        fragment.show(manager, "dialog");
    }

    //shows detail of the selected item
    public void onMilkteaSelected(Cursor milktea) {
        DetailFragment detail = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", milktea.getString(1));
        bundle.putInt("cup", milktea.getInt(2));
        bundle.putString("time", milktea.getString(3));
        bundle.putInt("rating", milktea.getInt(4));
        bundle.putString("timestamp", milktea.getString(5));

        detail.setArguments(bundle);

        //swap the fragments
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLeft, new MilkteaFragment())
                .replace(R.id.containerRight, detail)
                .addToBackStack(null)
                .commit();
    }
}