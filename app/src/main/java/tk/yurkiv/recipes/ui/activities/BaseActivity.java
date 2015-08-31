package tk.yurkiv.recipes.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DRAWER_ITEM_ID_KEY="drawer_item_id_key";

    @InjectView(R.id.drawer_layout) protected DrawerLayout drawerLayout;
    @InjectView(R.id.nav_view) protected NavigationView navigationView;
    @InjectView(R.id.content_frame) protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.inject(this);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()){
            case R.id.home:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(FragmentHolderActivity.class, 0);
                return true;
            case R.id.course:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(CategoryActivity.class, 1);
                return true;
            case R.id.cuisine:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(CategoryActivity.class, 2);
                return true;
            case R.id.holiday:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(CategoryActivity.class, 3);
                return true;
            case R.id.diets:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(FragmentHolderActivity.class, 4);
                return true;
            case R.id.allergy:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(FragmentHolderActivity.class, 5);
                return true;
            case R.id.favourites:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(FragmentHolderActivity.class, 6);
                return true;
            case R.id.shopping_list:
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                displayActivity(FragmentHolderActivity.class, 7);
                return true;
            default:
                return true;
        }
    }

    private void displayActivity(Class aClass, int drawer_item_index) {
        Intent intent = new Intent(this, aClass);
        intent.putExtra(DRAWER_ITEM_ID_KEY, drawer_item_index);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();// finishes the current activity
    }
}
