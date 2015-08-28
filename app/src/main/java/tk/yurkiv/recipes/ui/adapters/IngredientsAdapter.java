package tk.yurkiv.recipes.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Ingredient;

/**
 * Created by yurkiv on 26.08.2015.
 */
public class IngredientsAdapter extends BaseAdapter {

    private Context context;
    private List<Ingredient> ingredients;
    LayoutInflater inflater;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.context=context;
        this.ingredients=ingredients;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view != null) {
            ((ViewHolder) view.getTag()).chbIngredientItem.setTag(getItem(position));
        } else {
            view = inflater.inflate(R.layout.item_ingredient, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.chbIngredientItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Ingredient ingredient = (Ingredient) holder.chbIngredientItem.getTag();
                    ingredient.setSelected(buttonView.isChecked());
                }
            });
            view.setTag(holder);
            holder.chbIngredientItem.setTag(getItem(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.chbIngredientItem.setText(getItem(position).getName());
        holder.chbIngredientItem.setChecked(getItem(position).isSelected());

        return view;
    }

    public void uncheckAllChildrenCascade(ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof CheckBox) {
                ((CheckBox) v).setChecked(false);
                getItem(i).setSelected(false);
            } else if (v instanceof ViewGroup) {
                uncheckAllChildrenCascade((ViewGroup) v);
            }
        }
    }

    public List<String> getAllChecked(){
        List<String> ingredients=new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).isSelected()){
                ingredients.add(getItem(i).getName());
            }
        }
        return ingredients;
    }

    static class ViewHolder {

        @InjectView(R.id.chbIngredientItem) protected CheckBox chbIngredientItem;

        public ViewHolder(View itemView) {
            ButterKnife.inject(this, itemView);
        }
    }
}
