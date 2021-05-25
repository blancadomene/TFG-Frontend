package com.blancadomene.tfg;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TimePickerDialog picker;
    private EditText eText;
    private LinearLayout linearLayout = null;
    private View view = null;
    private Button button = null;
    private ArrayList<Ride> searches = new ArrayList<>();
    private boolean[] week = new boolean[7];


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        linearLayout = view.findViewById(R.id.fragment_search_result_container);
        button = view.findViewById(R.id.fragment_search_travel_button);
        button.setOnClickListener(v -> showResultList(v));

        eText = view.findViewById(R.id.fragment_search_departure_hour);
        eText.setOnClickListener(v -> showTimePickerDialog(v));

        return view;
    }

    public void showResultList(View view) {
        // TODO: Take data from editTexts
        // TODO: not null
        EditText edText;
        edText = (EditText) getActivity().findViewById(R.id.fragment_search_departure_point);
        String fsDeparturePoint = eText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_search_departure_point_radius);
        String fsDeparturePointRadius = eText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_search_arrival_point);
        String fsArrivalPoint = eText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_search_arrival_point_radius);
        String fsArrivalPointRadius = eText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_search_departure_hour);
        String fsDepartureHour = eText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_search_passengers_number);
        String fsPassengersNumber = eText.getText().toString();
        availableDaysOfWeek viewAv = (availableDaysOfWeek) getActivity().findViewById(R.id.fragment_search_days_of_week_view);
        boolean[] fsAvailableDaysOfWeek = viewAv.getSelectedDaysOfWeek();


        // TODO: Search with editTexts parameters and add searches to array (same name)
        // TODO: delete examples and iterate searches array
        searches.clear();
        Calendar exStartDate = new GregorianCalendar(2001, 1, 1);
        Calendar exEndDate = new GregorianCalendar(2002, 2, 2);
        Calendar exDepHour = new GregorianCalendar(1999, 9, 9, 13, 01);
        Calendar exArrHour = new GregorianCalendar(1999, 9, 9, 17, 14);
        User exUser = new User("Eren", "Yaeger", "erenthetitan@gmail.com", "111111111");


        searches.add(new Ride(exStartDate, exEndDate, "Almería", exDepHour, "Granada", exArrHour, 3, new BigDecimal("1.11"), exUser));
        searches.add(new Ride(exStartDate, exEndDate, "Valencia", exDepHour, "Madriz", exArrHour, 3, new BigDecimal("2.22"), exUser));
        searches.add(new Ride(exStartDate, exEndDate, "Barcelona", exDepHour, "Badajoz", exArrHour, 3, new BigDecimal("3.33"), exUser));
        searches.add(new Ride(exStartDate, exEndDate, "Alfacar", exDepHour, "Viznar", exArrHour, 3, new BigDecimal("4.44"), exUser));
        searches.add(new Ride(exStartDate, exEndDate, "Toledo", exDepHour, "Lugo", exArrHour, 3, new BigDecimal("5.55"), exUser));
        //TODO: delete examples and iterate searches array

        linearLayout.removeAllViews();

        for (int i = 0; i < searches.size(); i++) {
            Ride ride = searches.get(i);
            View card = getInstrumentRideCard(ride);
            linearLayout.addView(card);
        }
    }

    private View getInstrumentRideCard(Ride ride) {
        View view = ride.getRideCardView(getActivity());
        view.setOnClickListener(v -> switchToDetailedRideView(ride));

        return view;
    }

    private void switchToDetailedRideView(Ride ride) {
        RideDetailsFragment rdf = new RideDetailsFragment(ride);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, rdf, null)
                .setReorderingAllowed(true)
                .addToBackStack("searchView")
                .commit();
    }

    private void showTimePickerDialog(View view) {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // TimePicker dialog, starts with actual hour
        picker = new TimePickerDialog(getActivity(),
                (tp, tpHour, tpMinute) -> eText.setText(tpHour + ":" + tpMinute), hour, minutes, true);
        picker.show();
    }
}


