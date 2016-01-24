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


        Button searchButton = (Button)rootView.findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "View History");

                //Display milktea info
                //MilkteaDisplayTask task = new MilkteaDisplayTask();
                //task.execute("");
            }
        });

        
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

    /**
     * A background task to search for milktea
     */
    /*
    public class MilkteaDisplayTask extends AsyncTask<String, Void, ArrayList<Milktea>> {

        protected ArrayList<Milktea> doInBackground(String... params){
            final View rootView = inflator.inflate(R.layout.fragment_milktea, R.id.container, false);

            String milktea = params[0];

            ArrayList<Milktea> milkteas = new ArrayList<Milktea>();
            String[] cols = new String[]{MilkteaDatabase.MilkteaEntry.COL_TITLE};
            int[] ids = new int[]{R.id.txtItem};
            ArrayList<Milktea> list = new ArrayList<Milktea>();


            try {
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
            } catch (NullPointerException e) {
            }

            /*
            //construct the url for the omdbapi API
            String urlString = "";
            try {
                urlString = "http://www.omdbapi.com/?s=" + URLEncoder.encode(milktea, "UTF-8") + "&type=movie";
            }catch(UnsupportedEncodingException uee){
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            ArrayList<Milktea> milkteas = new ArrayList<Milktea>();

            try {

                URL url = new URL(urlString);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                String results = buffer.toString();

                milkteas = parseMilkteaJSONData(results); //convert JSON results into Milktea objects
            }
            catch (IOException e) {
                return null;
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (final IOException e) {
                    }
                }
            }

            return milkteas;
        }


        protected void onPostExecute(ArrayList<Milktea> milkteas){
            if(milkteas != null) {
                adapter.clear();
                adapter.addAll(milkteas);
            }
        }
        */


    /**
         * Parses a JSON-format String into a list of Milktea objects
         */
    /*
        public ArrayList<Milktea> parseMilkteaJSONData(String json){
            ArrayList<Milktea> milkteas = new ArrayList<Milktea>();

            try {
                JSONArray milkteaJsonArray = new JSONObject(json).getJSONArray("Search"); //get array from "search" key
                for(int i=0; i<milkteaJsonArray.length(); i++){
                    JSONObject milkteaJsonObject = milkteaJsonArray.getJSONObject(i); //get ith object from array
                    Milktea milktea = new Milktea();
                    milktea.title = milkteaJsonObject.getString("Title"); //get title from object
                    milktea.cup = Integer.parseInt(milkteaJsonObject.getString("Cup")); //get year from object
                    milktea.time = milkteaJsonObject.getString("Time"); //get imdb from object
                    milktea.desc = milkteaJsonObject.getString("Description"); //get poster from object

                    milkteas.add(milktea);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing json", e);
                return null;
            }

            return milkteas;
        }
*/
}