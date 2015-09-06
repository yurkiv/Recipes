package tk.yurkiv.recipes.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.ui.fragments.AllergyFragment;
import tk.yurkiv.recipes.ui.fragments.DietFragment;
import tk.yurkiv.recipes.ui.fragments.FavouritesFragment;
import tk.yurkiv.recipes.ui.fragments.HomeFragment;
import tk.yurkiv.recipes.ui.fragments.ShoppingListFragment;

public class FragmentHolderActivity extends BaseActivity {

    private Toolbar toolbar;
    private View toolbarShadow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_fragment_holder, frameLayout);
        Intent intent = getIntent();
        int fragmentIndex=intent.getIntExtra(DRAWER_ITEM_ID_KEY, 0);

        toolbarShadow = findViewById(R.id.toolbar_shadow);
        toolbar = (Toolbar) findViewById(R.id.toolbar_fragment_holder);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("");

        navigationView.getMenu().getItem(fragmentIndex).setChecked(true);

        switch (fragmentIndex) {
            case 0:
                displayFragment(new HomeFragment());
                setTitle("Home");
                toolbarShadow.setVisibility(View.VISIBLE);
                break;
            case 4:
                displayFragment(new DietFragment());
                break;
            case 5:
                displayFragment(new AllergyFragment());
                break;
            case 6:
                displayFragment(new FavouritesFragment());
                toolbarShadow.setVisibility(View.VISIBLE);
                break;
            case 7:
                displayFragment(new ShoppingListFragment());
                toolbarShadow.setVisibility(View.VISIBLE);
                break;
            case 8:

                break;
            case 9:

                break;
        }

    }

    private void displayFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
