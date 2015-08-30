package tk.yurkiv.recipes.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Ingredient;
import tk.yurkiv.recipes.ui.adapters.IngredientsAdapter;

public class ShoppingListFragment extends Fragment {

    private static final String TAG = ShoppingListFragment.class.getSimpleName();

    @InjectView(R.id.lvShopping) protected ListView lvShopping;

    private IngredientsAdapter ingredientsAdapter;

    public ShoppingListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> shoppingList=settings.getStringSet("shopping_list", new HashSet<String>());
        ingredientsAdapter = new IngredientsAdapter(getActivity(), getIngredients(shoppingList));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.inject(this, rootView);
        getActivity().setTitle("Shopping List");

        lvShopping.setAdapter(ingredientsAdapter);

        return rootView;
    }

    public List<Ingredient> getIngredients(Set<String> ing) {
        List<Ingredient> ingredients=new ArrayList<>();
        Ingredient ingredient=null;
        for (String ingredientStr:ing){
            ingredient=new Ingredient(ingredientStr, true);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

}
