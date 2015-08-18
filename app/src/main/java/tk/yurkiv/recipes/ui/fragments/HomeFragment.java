package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
        matches=getMatches();
        recipesAdapter=new RecipesAdapter(getActivity(), matches);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);

        rvRecipes.setHasFixedSize(true);
        rvRecipes.setLayoutManager(getGridLayoutManager());

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

    private List<Match> getMatches(){
        final List<Match> matches=new ArrayList<>();
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
        return matches;
    }

    private GridLayoutManager getGridLayoutManager() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                return (position % 3 == 0 ? 2 : 1); //for phone
                int index = position % 5;
                int span = 3;
                switch (index) {
                    case 0:
                        span = 2;
                        break;
                    case 1:
                        span = 2;
                        break;
                    case 2:
                        span = 2;
                        break;
                    case 3:
                        span = 3;
                        break;
                    case 4:
                        span = 3;
                        break;
                }
                return span;
            }
        });
        return gridLayoutManager;
    }

}
