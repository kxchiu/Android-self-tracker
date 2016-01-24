package edu.uw.cwc8.selftracker;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import edu.uw.selftracker.R;

/**
 * A simple {@link Fragment} subclass.
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
            TextView timeView = (TextView) rootView.findViewById(R.id.txtTime);
            TextView cupView = (TextView) rootView.findViewById(R.id.txtCup);

            String title = bundle.getString("title");
            int cup = bundle.getInt("cup");
            String time = bundle.getString("time");
            String desc = bundle.getString("desc");
            Milktea milktea = new Milktea(title, cup, time, desc); //recreate milktea

            titleView.setText(milktea.toString());
            timeView.setText("Milktea drank when: " + milktea.time);
            if(milktea.cup > 1) {
                cupView.setText("You drank " + milktea.cup + "cups of milktea");
            } else {
                cupView.setText("You drank " + milktea.cup + "cup of milktea");
            }

            /*
            Button favButton = (Button)rootView.findViewById(R.id.btnFavorite);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Favoriting...");

                }
            });
            */
        }

        return rootView;
    }



}