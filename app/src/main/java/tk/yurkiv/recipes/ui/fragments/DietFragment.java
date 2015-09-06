package tk.yurkiv.recipes.ui.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Category;

public class DietFragment extends Fragment {

    private static final String TAG = DietFragment.class.getSimpleName();
    @InjectView(R.id.viewpager) protected ViewPager viewPager;
    @InjectView(R.id.tabs) protected TabLayout tabLayout;

    public DietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_diet, container, false);
        ButterKnife.inject(this, rootView);
        getActivity().setTitle("Diets");
        final List<Category> categories=getCategories();

        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return RecipeListFragment.newInstance(null, null, null, null, null, null, categories.get(position).getSearchValue());
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

    public List<Category> getCategories() {
        List<Category> categories=new ArrayList<>();
        try {
            AssetManager assetManager = getActivity().getAssets();
            InputStream ims = assetManager.open("diet.json");

            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);
            Type listType = new TypeToken<List<Category>>(){}.getType();

            categories= (List<Category>) gson.fromJson(reader, listType);

            String packageName = getActivity().getPackageName();
            for(Category category : categories){
                category.setImageResourceId(getResources().getIdentifier(category.getImageId(), "drawable", packageName));
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
