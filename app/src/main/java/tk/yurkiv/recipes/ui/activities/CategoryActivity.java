package tk.yurkiv.recipes.ui.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Category;
import tk.yurkiv.recipes.ui.fragments.HomeFragment;

public class CategoryActivity extends BaseActivity {

    private MaterialViewPager mViewPager;
    private Toolbar toolbar;

    int activityId;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_category, frameLayout);
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        Intent intent = getIntent();
        activityId=intent.getIntExtra(DRAWER_ITEM_ID_KEY, 1);

        switch (activityId){
            case 1:
                categories=getCategoryRes("course.json");
                setTitle("Course");
                break;
            case 2:
                categories=getCategoryRes("cuisine.json");
                setTitle("World Cuisine");
                break;
            case 3:
                categories=getCategoryRes("holiday.json");
                setTitle("Holiday");
                break;
        }

        navigationView.getMenu().getItem(activityId).setChecked(true);

        toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (activityId){
                    case 1:
                        return HomeFragment.newInstance(null, null, null, null, categories.get(position).getSearchValue(), null, null);
                    case 2:
                        return HomeFragment.newInstance(null, null, null, categories.get(position).getSearchValue(), null, null, null);
                    case 3:
                        return HomeFragment.newInstance(null, null, null, null, null, categories.get(position).getSearchValue(), null);
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

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                Drawable drawable = getResources().getDrawable(categories.get(page).getImageResourceId());
                drawable.setColorFilter(getResources().getColor(R.color.tint), PorterDuff.Mode.SRC_ATOP);
                return HeaderDesign.fromColorResAndDrawable(R.color.primary, drawable);
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }

    public List<Category> getCategoryRes(String res) {
        List<Category> categories=new ArrayList<>();
        try {
            AssetManager assetManager = getAssets();
            InputStream ims = assetManager.open(res);

            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);
            Type listType = new TypeToken<List<Category>>(){}.getType();

            categories= (List<Category>) gson.fromJson(reader, listType);

            String packageName = getPackageName();
            for(Category category : categories){
                category.setImageResourceId(getResources().getIdentifier(category.getImageId(), "drawable", packageName));
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
