package tk.yurkiv.recipes.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.api.YummlyApi;
import tk.yurkiv.recipes.api.YummlyService;
import tk.yurkiv.recipes.model.Ingredient;
import tk.yurkiv.recipes.model.NutritionEstimate;
import tk.yurkiv.recipes.model.YummlyRecipe;
import tk.yurkiv.recipes.ui.adapters.IngredientsAdapter;

public class RecipeActivity extends AppCompatActivity {

    private static final String TAG = "RecipeActivity";
    public static final String RECIPE_ID_KEY="recipe_key";
    public static final String RECIPE_RATING_KEY="recipe_rating_key";
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();


    @InjectView(R.id.toolbar) protected Toolbar toolbar;
    @InjectView(R.id.collapsing_toolbar) protected CollapsingToolbarLayout collapsingToolbar;
    @InjectView(R.id.progressBar) protected ProgressBar progressBar;

    @InjectView(R.id.cvIntro) protected CardView cvIntro;
    @InjectView(R.id.cvIngredients) protected CardView cvIngredients;
    @InjectView(R.id.cvDirections) protected CardView cvDirections;
    @InjectView(R.id.cvFlavors) protected CardView cvFlavors;

    @InjectView(R.id.backdrop) protected ImageView ivBackdrop;
    @InjectView(R.id.fab) protected FloatingActionButton fab;

    @InjectView(R.id.tvRating) protected TextView tvRating;
    @InjectView(R.id.tvTime) protected TextView tvTime;
    @InjectView(R.id.tvServ) protected TextView tvServ;
    @InjectView(R.id.tvIngCount) protected TextView tvIngCount;
    @InjectView(R.id.tvKcal) protected TextView tvKcal;

    @InjectView(R.id.pbSalty) protected RoundCornerProgressBar pbSalty;
    @InjectView(R.id.pbSavory) protected RoundCornerProgressBar pbSavory;
    @InjectView(R.id.pbSour) protected RoundCornerProgressBar pbSour;
    @InjectView(R.id.pbBitter) protected RoundCornerProgressBar pbBitter;
    @InjectView(R.id.pbSweet) protected RoundCornerProgressBar pbSweet;
    @InjectView(R.id.pbSpicy) protected RoundCornerProgressBar pbSpicy;

    @InjectView(R.id.lvIngredients) protected ListView lvIngredients;
    @InjectView(R.id.btnClearIngredients) protected Button btnClearIngredients;
    @InjectView(R.id.btnAddIngredients) protected Button btnAddIngredients;

    @InjectView(R.id.tvDirections) protected TextView btnDirections;

    private YummlyService yummlyService;

    private IngredientsAdapter ingredientsAdapter;
//    private NutritionAdapter nutritionAdapter;
    private String urlRecipeName;
    private String urlRecipeDirections;

    private SharedPreferences settings;
    private Set<String> favRecipesIds;
    private boolean isFavorite=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        final String recipeId = intent.getStringExtra(RECIPE_ID_KEY);
        final String recipeRating = intent.getStringExtra(RECIPE_RATING_KEY);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        favRecipesIds=settings.getStringSet("fav", new HashSet<String>());
        if (favRecipesIds.contains(recipeId)){
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
            isFavorite=true;
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setTitle("");

        progressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));

        ivBackdrop.setAlpha(0f);
        cvIntro.setScaleY(0);
        cvIntro.setScaleX(0);
        cvIngredients.setScaleY(0);
        cvIngredients.setScaleX(0);
        cvFlavors.setScaleY(0);
        cvFlavors.setScaleX(0);
        cvDirections.setScaleY(0);
        cvDirections.setScaleX(0);

//        fab.setScaleY(0);
//        fab.setScaleX(0);

        yummlyService=YummlyApi.getService();
        yummlyService.getRecipe(recipeId, new Callback<YummlyRecipe>() {
            @Override
            public void success(YummlyRecipe recipe, Response response) {
                collapsingToolbar.setTitle(recipe.getName());
                Picasso.with(RecipeActivity.this).load(recipe.getImages().get(0).getHostedSmallUrl() + "0").into(ivBackdrop);
                tvRating.setText(recipeRating);
                tvTime.setText(recipe.getTotalTime());
                tvServ.setText(String.valueOf(recipe.getNumberOfServings()) + " servings");
                tvIngCount.setText(String.valueOf(recipe.getIngredientLines().size()) + " count");
                tvKcal.setText(getEnergy(recipe.getNutritionEstimates()) + " kcal");

                if (recipe.getFlavors().getMeaty() != 0) {
                    pbSalty.setProgress(recipe.getFlavors().getSalty());
                    pbSavory.setProgress(recipe.getFlavors().getMeaty());
                    pbSour.setProgress(recipe.getFlavors().getSour());
                    pbBitter.setProgress(recipe.getFlavors().getBitter());
                    pbSweet.setProgress(recipe.getFlavors().getSweet());
                    pbSpicy.setProgress(recipe.getFlavors().getPiquant());
                } else {
                    cvFlavors.setVisibility(View.GONE);
                }

                ingredientsAdapter = new IngredientsAdapter(RecipeActivity.this, getIngredients(recipe.getIngredientLines()));
                lvIngredients.setAdapter(ingredientsAdapter);

                btnDirections.setText("Read full directions on " + recipe.getSource().getSourceDisplayName());
                urlRecipeDirections=recipe.getSource().getSourceRecipeUrl();
                urlRecipeName=recipe.getName();

                progressBar.setVisibility(View.GONE);
                animateCards();
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(findViewById(R.id.main_content),
                        "No Connection",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    favRecipesIds.remove(recipeId);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_outline));
                    isFavorite = false;
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
                    favRecipesIds.add(recipeId);
                    isFavorite = true;
                }
                SharedPreferences.Editor editor = settings.edit();
                editor.putStringSet("fav", favRecipesIds);
                editor.commit();
                Log.d(TAG, "favRecipesIds: " + favRecipesIds.toString());
            }
        });

        btnClearIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsAdapter.uncheckAllChildrenCascade(lvIngredients);
            }
        });

        btnAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> shoppingList=settings.getStringSet("shopping_list", new HashSet<String>());
                shoppingList.addAll(ingredientsAdapter.getAllChecked());
                Log.d(TAG, "getAllChecked: " + ingredientsAdapter.getAllChecked());

                SharedPreferences.Editor editor = settings.edit();
                editor.putStringSet("shopping_list", shoppingList);
                editor.commit();
                Log.d(TAG, "shoppingList: " + shoppingList.toString());

                Snackbar.make(findViewById(R.id.main_content),
                        ingredientsAdapter.getAllChecked().size()+" items added to shopping list.",
                        Snackbar.LENGTH_LONG)
                        .show();

            }
        });

        cvDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlRecipeDirections));
                startActivity(intent);
            }
        });
    }

    private void animateCards(){
        ivBackdrop.animate().alpha(1f).setDuration(500).setInterpolator(INTERPOLATOR);
//        fab.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
        cvIntro.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(300).setInterpolator(INTERPOLATOR).start();
        cvIngredients.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(400).setInterpolator(INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setListViewHeightBasedOnItems(lvIngredients);
                    }
                }).start();
        cvFlavors.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
        cvDirections.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(500).setInterpolator(INTERPOLATOR).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, urlRecipeName);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, urlRecipeDirections);
                startActivity(sharingIntent);
                return true;
            case R.id.action_open_web_link:
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlRecipeDirections));
                startActivity(intent);
                return true;
            case R.id.action_about:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<Ingredient> getIngredients(List<String> ing) {
        List<Ingredient> ingredients=new ArrayList<>();
        Ingredient ingredient=null;
        for (String ingredientStr:ing){
            ingredient=new Ingredient(ingredientStr, false);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public String getEnergy(List<NutritionEstimate> nutritionEstimates){
        for (NutritionEstimate nutritionEstimate : nutritionEstimates){
            if (nutritionEstimate.getAttribute().contains("ENERC_KJ")){
                return String.valueOf(nutritionEstimate.getValue());
            }
        }
        return "100";
    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }
}
