package tk.yurkiv.recipes.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.api.YummlyService;
import tk.yurkiv.recipes.model.Match;
import tk.yurkiv.recipes.ui.adapters.RecipesAdapter;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = CategoryActivity.class.getSimpleName();

    @InjectView(R.id.rvRecipes) protected RecyclerView rvRecipes;

    private RecipesAdapter recipesAdapter;
    private List<Match> matches;
    private YummlyService yummlyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
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
}
