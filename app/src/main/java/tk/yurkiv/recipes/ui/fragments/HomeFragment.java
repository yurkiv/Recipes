package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.leonardoxh.fakesearchview.FakeSearchView;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;
import java.util.HashMap;
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

public class HomeFragment extends Fragment implements FilterFragment.FilterCallback, FakeSearchView.OnSearchListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    public static final String ALLERGY_KEY = "allergy_key";
    public static final String CUISINE_KEY = "cuisine_key";
    public static final String COURSE_KEY = "course_key";
    public static final String HOLIDAY_KEY = "holiday_key";
    public static final String DIET_KEY = "diet_key";
    public static final String MAX_TOTAL_TIME = "max_total_time";
    public static final String MAX_ENERGY = "max_energy";

    @InjectView(R.id.progressActivity) protected ProgressActivity progressActivity;
    @InjectView(R.id.rvRecipes) protected RecyclerView rvRecipes;

    private RecyclerView.Adapter recipesAdapter;
    private List<Match> matches;
    private YummlyService yummlyService;

    private String q=null;
    private String allowedAllergy=null;
    private String allowedCuisine=null;
    private String allowedCourse=null;
    private String allowedHoliday=null;
    private String allowedDiet=null;
    private String maxTotalTime=null;
    private String maxEnergy=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        yummlyService=YummlyApi.getService();
        matches=new ArrayList<>();
        Log.d(TAG, toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);
        getActivity().setTitle(getString(R.string.home));

        progressActivity.showLoading();
        getMatches(0);
        recipesAdapter=new RecyclerViewMaterialAdapter(new RecipesAdapter(getActivity(), matches));
        rvRecipes.setLayoutManager(Utils.getGridLayoutManager(getActivity()));
        rvRecipes.setHasFixedSize(true);
        EndlessRecyclerOnScrollListener scrollListener=new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "currentPage=" + currentPage);
                getMatches(currentPage);
            }
        };

        rvRecipes.addOnScrollListener(scrollListener);
        rvRecipes.setAdapter(recipesAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), rvRecipes, scrollListener);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_fragment, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        FakeSearchView fakeSearchView = (FakeSearchView) MenuItemCompat.getActionView(menuItem);
        fakeSearchView.setOnSearchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                FilterFragment filterFragment=new FilterFragment();
                filterFragment.setTargetFragment(this, 0);
                filterFragment.show(getActivity().getSupportFragmentManager(), "FOLDER_SELECTOR");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public String toString() {
        return "RecipeListFragment{" +
                "q='" + q + '\'' +
                ", allowedAllergy='" + allowedAllergy + '\'' +
                ", allowedCuisine='" + allowedCuisine + '\'' +
                ", allowedCourse='" + allowedCourse + '\'' +
                ", allowedHoliday='" + allowedHoliday + '\'' +
                '}';
    }

    @Override
    public void onFilter(HashMap<String, String> filters) {
        Log.d(TAG, filters.toString());

        allowedAllergy=filters.get(ALLERGY_KEY);
        allowedCuisine=filters.get(CUISINE_KEY);
        allowedCourse=filters.get(COURSE_KEY);
        allowedHoliday=filters.get(HOLIDAY_KEY);
        allowedDiet=filters.get(DIET_KEY);
        maxTotalTime=filters.get(MAX_TOTAL_TIME);
        maxEnergy=filters.get(MAX_ENERGY);

        matches.clear();
        getMatches(0);
    }

    private void getMatches(int page){
        yummlyService.getRecipes(q, 20, 20*page, allowedAllergy, allowedCuisine,
                allowedCourse, allowedHoliday, allowedDiet, maxTotalTime, maxEnergy, new Callback<YummlyRecipesListResponse>() {
                    @Override
                    public void success(YummlyRecipesListResponse yummlyRecipesListResponse, Response response) {
                        matches.addAll(yummlyRecipesListResponse.getMatches());
                        recipesAdapter.notifyDataSetChanged();
                        progressActivity.showContent();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "Failed call: " + error.toString());
                        progressActivity.showError(
                                getActivity().getResources().getDrawable(R.drawable.ic_connection_error),
                                getString(R.string.no_connection),
                                getString(R.string.no_connection_text),
                                getString(R.string.try_again),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        progressActivity.showLoading();
                                        getMatches(0);
                                    }
                                });
                    }
                });
    }

    @Override
    public void onSearch(FakeSearchView fakeSearchView, CharSequence charSequence) {

    }

    @Override
    public void onSearchHint(FakeSearchView fakeSearchView, CharSequence charSequence) {
        q = charSequence.toString();
        matches.clear();
        getMatches(0);
    }
}
