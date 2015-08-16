package tk.yurkiv.recipes.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tk.yurkiv.recipes.ui.fragments.TabCuisineFragment;
import tk.yurkiv.recipes.ui.fragments.TabDietFragment;
import tk.yurkiv.recipes.ui.fragments.TabHolidayFragment;

/**
 * Created by yurkiv on 16.08.2015.
 */
public class ExplorePagerAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Cuisine", "Holiday", "Diet" };
    private Context context;

    public ExplorePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabCuisineFragment tabCuisineFragment = new TabCuisineFragment();
                return tabCuisineFragment;
            case 1:
                TabHolidayFragment tabHolidayFragment = new TabHolidayFragment();
                return tabHolidayFragment;
            case 2:
                TabDietFragment tabDietFragment = new TabDietFragment();
                return tabDietFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
