package edu.uw.cwc8.selftracker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

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
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class MilkteaFragment extends Fragment {

    private static final String TAG = "MilkteaFragment";

    //private ArrayAdapter<Milktea> adapter; //adapter for list view
    private SimpleCursorAdapter adapter; //adapter for list view

    private OnMilkteaSelectionListener callback;

    public interface OnMilkteaSelectionListener {
        public void onMilkteaSelected(Milktea milktea);
    }


    public MilkteaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            callback = (OnMilkteaSelectionListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnMilkteaSelectionListener");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_milktea, container, false);




        
        Button addButton = (Button)rootView.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Add something");
            }
        });


        /** List View **/
        //model (starts out empty)
        ArrayList<Milktea> list = new ArrayList<Milktea>();

        String[] cols = new String[]{MilkteaDatabase.MilkteaEntry.COL_TITLE};
        int[] ids = new int[]{R.id.txtItem};

        adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item,
                MilkteaDatabase.queryDatabase(getActivity()),
                cols, ids,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );

        //support ListView or GridView
        AdapterView listView = (AdapterView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button searchButton = (Button)rootView.findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "View History");
            }
        });

        //respond to item clicking
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Milktea milktea = (Milktea) parent.getItemAtPosition(position);
                Log.i(TAG, "selected: " + milktea.toString());

                //swap the fragments to show the detail
                ((OnMilkteaSelectionListener) getActivity()).onMilkteaSelected(milktea);

            }
        });

        return rootView;
    }

}