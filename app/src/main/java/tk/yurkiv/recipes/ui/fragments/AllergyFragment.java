package tk.yurkiv.recipes.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Category;
import tk.yurkiv.recipes.util.Utils;

public class AllergyFragment extends Fragment {

    private static final String TAG = AllergyFragment.class.getSimpleName();
    @InjectView(R.id.viewpager) protected ViewPager viewPager;
    @InjectView(R.id.tabs) protected TabLayout tabLayout;

    public AllergyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_diet, container, false);
        ButterKnife.inject(this, rootView);

        getActivity().setTitle(getString(R.string.allergy));
        final List<Category> categories=Utils.getCategoryRes(getActivity(), "allergy.json");

        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return RecipeListFragment.newInstance(null, null, categories.get(position).getSearchValue(), null, null, null, null);
            }

            @Override
            public int getCount() {
                return categories.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return categories.get(position).getLongDescription();
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }
}
