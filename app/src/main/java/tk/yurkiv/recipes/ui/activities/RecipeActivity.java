package tk.yurkiv.recipes.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.squareup.picasso.Picasso;

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
    @InjectView(R.id.nestedScrollView) protected NestedScrollView nestedScrollView;


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

    @InjectView(R.id.btnDirections) protected Button btnDirections;

    private YummlyService yummlyService;

    private IngredientsAdapter ingredientsAdapter;
//    private NutritionAdapter nutritionAdapter;
    private String urlRecipeDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.inject(this);

        cvIntro.setScaleY(0);
        cvIntro.setScaleX(0);
        cvIngredients.setScaleY(0);
        cvIngredients.setScaleX(0);
        cvFlavors.setScaleY(0);
        cvFlavors.setScaleX(0);
        cvDirections.setScaleY(0);
        cvDirections.setScaleX(0);
        fab.setScaleY(0);
        fab.setScaleX(0);

        Intent intent = getIntent();
        final String recipeId = intent.getStringExtra(RECIPE_ID_KEY);
        final String recipeRating = intent.getStringExtra(RECIPE_RATING_KEY);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

                if (recipe.getFlavors() != null) {
                    pbSalty.setProgress(recipe.getFlavors().getSalty());
                    pbSavory.setProgress(recipe.getFlavors().getMeaty());
                    pbSour.setProgress(recipe.getFlavors().getSour());
                    pbBitter.setProgress(recipe.getFlavors().getBitter());
                    pbSweet.setProgress(recipe.getFlavors().getSweet());
                    pbSpicy.setProgress(recipe.getFlavors().getPiquant());
                }

                ingredientsAdapter = new IngredientsAdapter(RecipeActivity.this, getIngredients(recipe.getIngredientLines()));
                lvIngredients.setAdapter(ingredientsAdapter);
                setListViewHeightBasedOnItems(lvIngredients);
                nestedScrollView.scrollTo(0,0);

                btnDirections.setText("Read full directions on " + recipe.getSource().getSourceDisplayName());
                urlRecipeDirections=recipe.getSource().getSourceRecipeUrl();

                fab.animate().scaleY(1).scaleX(1).setDuration(300).setInterpolator(INTERPOLATOR).start();
                cvIntro.animate().scaleY(1).scaleX(1).setDuration(300).setInterpolator(INTERPOLATOR).start();
                cvIngredients.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR).start();
                cvFlavors.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR).start();
                cvDirections.animate().scaleY(1).scaleX(1).setDuration(300).setStartDelay(300).setInterpolator(INTERPOLATOR).start();


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        btnClearIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsAdapter.uncheckAllChildrenCascade(lvIngredients);
            }
        });

        btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlRecipeDirections));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
