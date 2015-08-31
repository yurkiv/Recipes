package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.api.YummlyApi;
import tk.yurkiv.recipes.api.YummlyService;
import tk.yurkiv.recipes.model.Match;
import tk.yurkiv.recipes.model.YummlyRecipesListResponse;
import tk.yurkiv.recipes.ui.adapters.RecipesAdapter;
import tk.yurkiv.recipes.util.EndlessRecyclerOnScrollListener;
import tk.yurkiv.recipes.util.Utils;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String QUERY_KEY = "query_key";
    private static final String INGREDIENT_KEY = "ingredient_key";
    private static final String ALLERGY_KEY = "allergy_key";
    private static final String CUISINE_KEY = "cuisine_key";
    private static final String COURSE_KEY = "course_key";
    private static final String HOLIDAY_KEY = "holiday_key";
    private static final String DIET_KEY = "diet_key";

    @InjectView(R.id.rvRecipes) protected RecyclerView rvRecipes;

    private RecyclerView.Adapter recipesAdapter;
    private List<Match> matches;
    private YummlyService yummlyService;

    private String q;
    private String allowedIngredient;
    private String allowedAllergy;
    private String allowedCuisine;
    private String allowedCourse;
    private String allowedHoliday;
    private String allowedDiet;

    public static HomeFragment newInstance(String q,
                                           String allowedIngredient,
                                           String allowedAllergy,
                                           String allowedCuisine,
                                           String allowedCourse,
                                           String allowedHoliday,
                                           String allowedDiet) {
        HomeFragment homeFragment=new HomeFragment();
        Bundle bundle=new Bundle();
        bundle.putString(QUERY_KEY, q);
        bundle.putString(INGREDIENT_KEY, allowedIngredient);
        bundle.putString(ALLERGY_KEY, allowedAllergy);
        bundle.putString(CUISINE_KEY, allowedCuisine);
        bundle.putString(COURSE_KEY, allowedCourse);
        bundle.putString(HOLIDAY_KEY, allowedHoliday);
        bundle.putString(DIET_KEY, allowedDiet);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        q=getArguments().getString(QUERY_KEY, null);
        allowedIngredient=getArguments().getString(INGREDIENT_KEY, null);
        allowedAllergy=getArguments().getString(ALLERGY_KEY, null);
        allowedCuisine=getArguments().getString(CUISINE_KEY, null);
        allowedCourse=getArguments().getString(COURSE_KEY, null);
        allowedHoliday=getArguments().getString(HOLIDAY_KEY, null);
        allowedDiet=getArguments().getString(DIET_KEY, null);

        Log.d(TAG, toString());

        yummlyService= YummlyApi.getService();
        matches=new ArrayList<>();
        getFirstMatches();
        recipesAdapter=new RecyclerViewMaterialAdapter(new RecipesAdapter(getActivity(), matches));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);
        rvRecipes.setLayoutManager(Utils.getGridLayoutManager(getActivity()));

        rvRecipes.setHasFixedSize(true);

        EndlessRecyclerOnScrollListener scrollListener=new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "currentPage=" + currentPage);
                yummlyService.getRecipes(q, 20, 20*currentPage, allowedIngredient, allowedAllergy, allowedCuisine,
                        allowedCourse, allowedHoliday, allowedDiet, new Callback<YummlyRecipesListResponse>() {
                            @Override
                            public void success(YummlyRecipesListResponse yummlyRecipesListResponse, Response response) {
                                matches.addAll(yummlyRecipesListResponse.getMatches());
                                recipesAdapter.notifyDataSetChanged();
                                Log.d(TAG, "matches.size: " + matches.size());
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d(TAG, "Failed call: " + error.toString());
                            }
                        });
            }
        };

        rvRecipes.addOnScrollListener(scrollListener);
        rvRecipes.setAdapter(recipesAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), rvRecipes, scrollListener);


        return rootView;
    }

    private void getFirstMatches(){
        yummlyService.getRecipes(q, 20, 0, allowedIngredient, allowedAllergy, allowedCuisine,
                allowedCourse, allowedHoliday, allowedDiet, new Callback<YummlyRecipesListResponse>() {
                    @Override
                    public void success(YummlyRecipesListResponse yummlyRecipesListResponse, Response response) {
                        matches.addAll(yummlyRecipesListResponse.getMatches());
                        recipesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "Failed call: " + error.toString());
                    }
                });
    }


    public String toString() {
        return "HomeFragment{" +
                "q='" + q + '\'' +
                ", allowedIngredient='" + allowedIngredient + '\'' +
                ", allowedAllergy='" + allowedAllergy + '\'' +
                ", allowedCuisine='" + allowedCuisine + '\'' +
                ", allowedCourse='" + allowedCourse + '\'' +
                ", allowedHoliday='" + allowedHoliday + '\'' +
                '}';
    }

}
