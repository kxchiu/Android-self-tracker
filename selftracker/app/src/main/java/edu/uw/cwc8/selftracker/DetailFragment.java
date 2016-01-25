package edu.uw.cwc8.selftracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *  The DetailFragment displays details for each activity,
 *  including # of cups drank, time, and how the user feels about the milktea.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = "DetailFragment";

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getArguments();

        if(bundle != null) {
            TextView titleView = (TextView) rootView.findViewById(R.id.txtTitle);
            TextView cupView = (TextView) rootView.findViewById(R.id.txtCup);
            TextView timeView = (TextView) rootView.findViewById(R.id.txtTime);
            TextView ratingView = (TextView) rootView.findViewById(R.id.txtGivenRating);

            String title = bundle.getString("title");
            int cup = bundle.getInt("cup");
            String time = bundle.getString("time");
            int rating = bundle.getInt("rating");
            String tStamp = bundle.getString("timestamp");
            Log.v(TAG, String.valueOf(rating));
            Milktea milktea = new Milktea(title, cup, time, rating, tStamp); //recreate milktea

            //set text for title
            titleView.setText(milktea.toString());

            //set text for number of cups
            if(milktea.cup > 1) {
                cupView.setText("You drank " + milktea.cup + " cups of milktea");
            } else {
                cupView.setText("You drank " + milktea.cup + " cup of milktea");
            }

            //set text for time
            timeView.setText("Milktea drank at: \n" + milktea.timestamp);

            //set text for rating
            if (rating == 1) {
                ratingView.setText(rating + ": The milktea isn't tasty at all \n >:(");
            } else if (rating == 2) {
                ratingView.setText(rating + ": The milktea is not so good...");
            } else if (rating == 3) {
                ratingView.setText(rating + ": The milktea is ok.");
            } else if (rating == 4) {
                ratingView.setText(rating + ": The milktea is good. You are lovin' it.");
            } else if (rating == 5) {
                ratingView.setText(rating + ": OMG IT IS AWESOME!!! \n ε=ε=(ノ≧∇≦)ノ");
            } else {
                ratingView.setText("I have no clue what you are thinking \n _(:з」∠)_");
            }


        }

        return rootView;
    }



}