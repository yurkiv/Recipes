package tk.yurkiv.recipes.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.NutritionEstimate;

/**
 * Created by yurkiv on 19.08.2015.
 */
public class NutritionAdapter extends ArrayAdapter<NutritionEstimate> {

    public NutritionAdapter(Context context, List<NutritionEstimate> nutritionEstimates) {
        super(context, R.layout.item_nutrition, nutritionEstimates);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        NutritionEstimate nutritionEstimate=getItem(position);
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.item_nutrition, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.tvNutritionName.setText(nutritionEstimate.getDescription());
        holder.tvNutritionGr.setText(nutritionEstimate.getValue()+" "+nutritionEstimate.getUnit().getPluralAbbreviation());

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.tvNutritionName) TextView tvNutritionName;
        @InjectView(R.id.tvNutritionGr) TextView tvNutritionGr;
        @InjectView(R.id.tvNutritionPercent) TextView tvNutritionPercent;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
