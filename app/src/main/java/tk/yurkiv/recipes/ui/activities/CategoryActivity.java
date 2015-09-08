package tk.yurkiv.recipes.ui.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.List;

import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Category;
import tk.yurkiv.recipes.ui.fragments.RecipeListFragment;
import tk.yurkiv.recipes.util.Utils;

public class CategoryActivity extends BaseActivity {

    private MaterialViewPager viewPager;
    private Toolbar toolbar;

    int activityId;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_category, frameLayout);
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        Intent intent = getIntent();
        activityId=intent.getIntExtra(DRAWER_ITEM_ID_KEY, 1);

        switch (activityId){
            case 1:
                categories=Utils.getCategoryRes(CategoryActivity.this, "course.json");
                setTitle(getResources().getString(R.string.course));
                break;
            case 2:
                categories=Utils.getCategoryRes(CategoryActivity.this, "cuisine.json");
                setTitle(getResources().getString(R.string.world_cuisine));
                break;
            case 3:
                categories=Utils.getCategoryRes(CategoryActivity.this, "holiday.json");
                setTitle(getResources().getString(R.string.holiday));
                break;
        }

        navigationView.getMenu().getItem(activityId).setChecked(true);

        toolbar = viewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        viewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (activityId){
                    case 1:
                        return RecipeListFragment.newInstance(null, null, null, null, categories.get(position).getSearchValue(), null, null);
                    case 2:
                        return RecipeListFragment.newInstance(null, null, null, categories.get(position).getSearchValue(), null, null, null);
                    case 3:
                        return RecipeListFragment.newInstance(null, null, null, null, null, categories.get(position).getSearchValue(), null);
                }
                return null;
            }

            @Override
            public int getCount() {
                return categories.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return categories.get(position).getName();
            }
        });

        viewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                Drawable drawable = getResources().getDrawable(categories.get(page).getImageResourceId());
                drawable.setColorFilter(getResources().getColor(R.color.tint), PorterDuff.Mode.SRC_ATOP);
                return HeaderDesign.fromColorResAndDrawable(R.color.primary, drawable);
            }
        });

        viewPager.getViewPager().setOffscreenPageLimit(viewPager.getViewPager().getAdapter().getCount());
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());

    }
}
