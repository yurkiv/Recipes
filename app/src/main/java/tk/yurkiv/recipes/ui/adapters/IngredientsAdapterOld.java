package tk.yurkiv.recipes.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Ingredient;

/**
 * Created by yurkiv on 19.08.2015.
 */
public class IngredientsAdapterOld extends RecyclerView.Adapter<IngredientsAdapterOld.ViewHolder> {

    private Context context;
    private List<Ingredient> ingredients;

    public IngredientsAdapterOld(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
        Log.d("TAG", String.valueOf(ingredients.size()));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.chbIngredientItem.setText(ingredients.get(position).getName());
        Log.d("TAG", ingredients.get(position).getName());

        holder.chbIngredientItem.setChecked(ingredients.get(position).isSelected());
        holder.chbIngredientItem.setTag(ingredients.get(position));

        holder.chbIngredientItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Ingredient ingredient = (Ingredient) cb.getTag();
                ingredient.setSelected(cb.isChecked());
                ingredients.get(position).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.chbIngredientItem) protected CheckBox chbIngredientItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
