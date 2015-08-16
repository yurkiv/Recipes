package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.yurkiv.recipes.R;

public class TabCuisineFragment extends Fragment {


    public TabCuisineFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_cuisine, container, false);
    }


}
