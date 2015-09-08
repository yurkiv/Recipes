package tk.yurkiv.recipes.ui.fragments;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vlonjatg.progressactivity.ProgressActivity;

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
import tk.yurkiv.recipes.ui.activities.RecipeActivity;
import tk.yurkiv.recipes.ui.adapters.FavoritesRecipesAdapter;

public class FavoritesFragment extends Fragment {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    @InjectView(R.id.progressActivity) protected ProgressActivity progressActivity;
    @InjectView(R.id.rvFavoritesRecipes) protected RecyclerView rvFavouritesRecipes;

    private FavoritesRecipesAdapter favoritesRecipesAdapter;
    private List<YummlyRecipe> yummlyRecipes;
    private YummlyService yummlyService;

    public FavoritesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yummlyService=YummlyApi.getService();
        yummlyRecipes=new ArrayList<>();
        favoritesRecipesAdapter=new FavoritesRecipesAdapter(getActivity(), yummlyRecipes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.inject(this, rootView);

        getActivity().setTitle(getString(R.string.favorites));
        progressActivity.showLoading();

        rvFavouritesRecipes.setHasFixedSize(true);
        rvFavouritesRecipes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvFavouritesRecipes.setAdapter(favoritesRecipesAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFavouritesYummlyRecipes();
    }

    private void updateFavouritesYummlyRecipes() {
        Set<String> favRecipesIds=PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getStringSet(RecipeActivity.FAVORITE_RECIPE_KEY, new HashSet<String>());
        if (favRecipesIds.isEmpty()){
            progressActivity.showEmpty(
                    getResources().getDrawable(R.drawable.ic_food_white_48dp),
                    getString(R.string.empty_favorites),
                    getString(R.string.empty_favorites_text)
            );
            return;
        }
        Log.d(TAG, "favRecipesIds: " + favRecipesIds.toString());
        yummlyRecipes.clear();
        for (String id : favRecipesIds){
            yummlyService.getRecipe(id, new Callback<YummlyRecipe>() {
                @Override
                public void success(YummlyRecipe yummlyRecipe, Response response) {
                    yummlyRecipes.add(yummlyRecipe);
                    favoritesRecipesAdapter.notifyDataSetChanged();
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
                                    updateFavouritesYummlyRecipes();
                                }
                            });
                }
            });
        }
    }
}
