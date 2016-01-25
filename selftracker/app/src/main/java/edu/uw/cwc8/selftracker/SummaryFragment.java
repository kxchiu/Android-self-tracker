package edu.uw.cwc8.selftracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 *  The SummaryFragment provides summary statistics for the user's activity.
 */
public class SummaryFragment extends Fragment {


    public SummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_summary, container, false);

        TextView sum = (TextView)rootView.findViewById(R.id.txtSum);
        TextView avg = (TextView)rootView.findViewById(R.id.txtAverage);
        TextView avgRtg = (TextView)rootView.findViewById(R.id.txtAvgRating);
        Cursor results = MilkteaDatabase.queryDatabase(getActivity());

        int cups = 0;
        int ratings = 0;
        int entries = results.getCount();

        while(results.moveToNext()){
            cups += results.getInt(2);
            ratings += results.getInt(4);
        }

        double average = (cups * 1.0) / entries;
        double avgRating = (ratings * 1.0) / entries;
        average = Math.round(average * 100.0) / 100.0;
        avgRating = Math.round(avgRating * 100.0) / 100.0;
        sum.setText("Total cups of milktea: " + cups + "\nTotal number of purchases:" + entries);
        avg.setText("Average cups per purchase: \n " + String.valueOf(average));
        avgRtg.setText("Average rating for milkteas: \n " + avgRating);

        results.close();

        return rootView;
    }

}