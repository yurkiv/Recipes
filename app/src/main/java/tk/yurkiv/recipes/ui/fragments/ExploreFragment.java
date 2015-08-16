package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.ui.adapters.ExplorePagerAdapter;

public class ExploreFragment extends Fragment {

    @InjectView(R.id.viewpager) protected ViewPager viewPager;
    @InjectView(R.id.tabs) protected TabLayout tabLayout;

    public ExploreFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.inject(this, rootView);
        viewPager.setAdapter(new ExplorePagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }
}