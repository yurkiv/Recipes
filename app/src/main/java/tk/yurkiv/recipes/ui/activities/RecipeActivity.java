package tk.yurkiv.recipes.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.api.YummlyApi;
import tk.yurkiv.recipes.api.YummlyService;
import tk.yurkiv.recipes.model.NutritionEstimate;
import tk.yurkiv.recipes.model.YummlyRecipe;
import tk.yurkiv.recipes.ui.adapters.IngredientsAdapter;
import tk.yurkiv.recipes.ui.adapters.NutritionAdapter;

public class RecipeActivity extends AppCompatActivity {

    public static final String RECIPE_ID_KEY="recipe_key";
    public static final String RECIPE_RATING_KEY="recipe_rating_key";

    @InjectView(R.id.toolbar) protected Toolbar toolbar;
    @InjectView(R.id.collapsing_toolbar) protected CollapsingToolbarLayout collapsingToolbar;
    @InjectView(R.id.cvIntro) protected CardView cvIntro;
    @InjectView(R.id.cvIngredients) protected CardView cvIngredients;
    @InjectView(R.id.cvDirections) protected CardView cvDirections;
    @InjectView(R.id.cvFlavors) protected CardView cvFlavors;
    @InjectView(R.id.backdrop) protected ImageView ivBackdrop;
    @InjectView(R.id.tvRating) protected TextView tvRating;
    @InjectView(R.id.tvTime) protected TextView tvTime;
    @InjectView(R.id.tvServ) protected TextView tvServ;
    @InjectView(R.id.tvIngCount) protected TextView tvIngCount;
    @InjectView(R.id.tvKcal) protected TextView tvKcal;
    @InjectView(R.id.tvIngredients) protected TextView tvIngredients;
    @InjectView(R.id.btnDirections) protected Button btnDirections;

    @InjectView(R.id.pbSalty) protected RoundCornerProgressBar pbSalty;
    @InjectView(R.id.pbSavory) protected RoundCornerProgressBar pbSavory;
    @InjectView(R.id.pbSour) protected RoundCornerProgressBar pbSour;
    @InjectView(R.id.pbBitter) protected RoundCornerProgressBar pbBitter;
    @InjectView(R.id.pbSweet) protected RoundCornerProgressBar pbSweet;
    @InjectView(R.id.pbSpicy) protected RoundCornerProgressBar pbSpicy;

//    @InjectView(R.id.rvIngredients) protected RecyclerView rvIngredients;

    private IngredientsAdapter ingredientsAdapter;

    private YummlyService yummlyService;
    private NutritionAdapter nutritionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        final String recipeId = intent.getStringExtra(RECIPE_ID_KEY);
        final String recipeRating = intent.getStringExtra(RECIPE_RATING_KEY);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        yummlyService= YummlyApi.getService();
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
                tvIngredients.setText(getIngredients(recipe.getIngredientLines()));
                btnDirections.setText(recipe.getSource().getSourceDisplayName());
                if (recipe.getFlavors()!=null){
                    pbSalty.setProgress(recipe.getFlavors().getSalty());
                    pbSavory.setProgress(recipe.getFlavors().getMeaty());
                    pbSour.setProgress(recipe.getFlavors().getSour());
                    pbBitter.setProgress(recipe.getFlavors().getBitter());
                    pbSweet.setProgress(recipe.getFlavors().getSweet());
                    pbSpicy.setProgress(recipe.getFlavors().getPiquant());
                }


                cvIntro.setVisibility(View.VISIBLE);
                cvIngredients.setVisibility(View.VISIBLE);
                cvDirections.setVisibility(View.VISIBLE);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

//        setupFlavorsViews();

//        ingredientsAdapter=new IngredientsAdapter(this, getIngredients());
////        IngredientsLinearLayoutManager layoutManager = new IngredientsLinearLayoutManager(this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(rvIngredients, LinearLayoutManager.VERTICAL, false);
//        layoutManager.setOverScrollMode(ViewCompat.OVER_SCROLL_NEVER);
//        rvIngredients.setLayoutManager(layoutManager);
//        rvIngredients.setAdapter(ingredientsAdapter);



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

    private void setupFlavorsViews(){

        Random random=new Random();


    }

    public String getIngredients(List<String> ing) {
        StringBuffer buffer=new StringBuffer();
        for (String ingredient:ing){
            buffer.append(ingredient);
            buffer.append("\n");
        }
        return buffer.toString();
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
