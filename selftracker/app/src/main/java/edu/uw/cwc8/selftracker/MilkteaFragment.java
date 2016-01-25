package edu.uw.cwc8.selftracker;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 *  The MilkteaFragment displays the observations in a listview.
 */
public class MilkteaFragment extends Fragment {

    private static final String TAG = "MilkteaFragment";

    private SimpleCursorAdapter adapter; //adapter for list view

    private OnMilkteaSelectionListener callback;
    private MilkteaActivity mActivity;

    public interface OnMilkteaSelectionListener {
        void onMilkteaSelected(Cursor milktea);
        void onShowDialog(AddFragment frag);
    }

    public MilkteaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.v(TAG, "Attached");

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

        mActivity = (MilkteaActivity)getActivity();
        /** List View **/
        final ArrayList<Milktea> list = new ArrayList<Milktea>();
        list.clear();
        String[] cols = new String[]{MilkteaDatabase.MilkteaEntry.COL_TIME};
        int[] ids = new int[]{R.id.txtItem};

        adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item,
                MilkteaDatabase.queryDatabase(getActivity()),
                cols, ids,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        Log.v(TAG, MilkteaDatabase.queryDatabase(getActivity()).toString());

        AdapterView listView = (AdapterView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //respond to item clicking
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor milktea = (Cursor) parent.getItemAtPosition(position);
                Log.i(TAG, "selected: " + milktea);

                //swap the fragments to show the detail
                ((OnMilkteaSelectionListener) getActivity()).onMilkteaSelected(milktea);

            }
        });

        //when the "Add Event" button is pressed
        Button addButton = (Button) rootView.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(TAG, "Add button pressed!");
                AddFragment addFrag = AddFragment.newInstance();

                ((OnMilkteaSelectionListener) getActivity()).onShowDialog(addFrag);

            }
        });

        return rootView;
    }

}