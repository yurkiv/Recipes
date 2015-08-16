package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.yurkiv.recipes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabHolidayFragment extends Fragment {


    public TabHolidayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_holiday, container, false);
    }


}
