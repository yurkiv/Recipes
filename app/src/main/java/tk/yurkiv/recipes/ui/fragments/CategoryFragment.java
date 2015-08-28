package tk.yurkiv.recipes.ui.fragments;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import tk.yurkiv.recipes.ui.adapters.CategoryAdapter;

public class CategoryFragment extends Fragment {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    @InjectView(R.id.rvCategory) protected RecyclerView rvCategory;

    private CategoryAdapter categoryAdapter;
    private List<Category> categories;

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories=getCategories();
//        getFirstMatches();
        categoryAdapter=new CategoryAdapter(getActivity(), categories);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.inject(this, rootView);
        getActivity().setTitle("Category");
        rvCategory.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCategory.setLayoutManager(linearLayoutManager);
        rvCategory.setAdapter(categoryAdapter);

        return rootView;
    }


    public List<Category> getCategories() {
        List<Category> categories=new ArrayList<>();
        try {
            AssetManager assetManager = getActivity().getAssets();
            InputStream ims = assetManager.open("course.json");

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
