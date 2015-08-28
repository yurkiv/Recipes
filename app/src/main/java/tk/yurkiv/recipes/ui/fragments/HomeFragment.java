package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @InjectView(R.id.rvRecipes) protected RecyclerView rvRecipes;

    private RecipesAdapter recipesAdapter;
    private List<Match> matches;
    private YummlyService yummlyService;


    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yummlyService= YummlyApi.getService();
        matches=new ArrayList<>();
        getFirstMatches();
        recipesAdapter=new RecipesAdapter(getActivity(), matches);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);

        rvRecipes.setHasFixedSize(true);
        rvRecipes.setLayoutManager(Utils.getGridLayoutManager(getActivity()));

        rvRecipes.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "currentPage=" + currentPage);
                yummlyService.getRecipesByQuery(null, 20, 20*currentPage, new Callback<YummlyRecipesListResponse>() {
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
        });

        rvRecipes.setAdapter(recipesAdapter);

        return rootView;
    }

    private void getFirstMatches(){
        yummlyService.getRecipesByQuery(null, 20, 0, new Callback<YummlyRecipesListResponse>() {
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

}
