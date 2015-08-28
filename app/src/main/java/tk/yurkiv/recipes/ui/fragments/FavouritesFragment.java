package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.api.YummlyApi;
import tk.yurkiv.recipes.api.YummlyService;
import tk.yurkiv.recipes.model.YummlyRecipe;
import tk.yurkiv.recipes.ui.adapters.FavouritesRecipesAdapter;
import tk.yurkiv.recipes.util.Utils;

public class FavouritesFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    @InjectView(R.id.rvFavouritesRecipes) protected RecyclerView rvFavouritesRecipes;

    private FavouritesRecipesAdapter favouritesRecipesAdapter;
    private List<YummlyRecipe> yummlyRecipes;
    private YummlyService yummlyService;

    public FavouritesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yummlyService=YummlyApi.getService();
        yummlyRecipes=new ArrayList<>();
        favouritesRecipesAdapter=new FavouritesRecipesAdapter(getActivity(), yummlyRecipes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.inject(this, rootView);

        rvFavouritesRecipes.setHasFixedSize(true);
        rvFavouritesRecipes.setLayoutManager(Utils.getGridLayoutManager(getActivity()));
        rvFavouritesRecipes.setAdapter(favouritesRecipesAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFavouritesYummlyRecipes();
    }

    private void updateFavouritesYummlyRecipes() {
        Set<String> favRecipesIds=PreferenceManager.getDefaultSharedPreferences(getActivity()).getStringSet("fav", new HashSet<String>());
        Log.d(TAG, "favRecipesIds: " + favRecipesIds.toString());
        yummlyRecipes.clear();
        for (String id : favRecipesIds){
            yummlyService.getRecipe(id, new Callback<YummlyRecipe>() {
                @Override
                public void success(YummlyRecipe yummlyRecipe, Response response) {
                    yummlyRecipes.add(yummlyRecipe);
                    favouritesRecipesAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, "Failed call: " + error.toString());
                }
            });
        }
    }
}
