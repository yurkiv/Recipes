package tk.yurkiv.recipes.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Category;
import tk.yurkiv.recipes.ui.activities.RecipeActivity;

/**
 * Created by yurkiv on 17.08.2015.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Category category=categories.get(position);

        holder.tvCategoryNameItem.setText(category.getName());
        holder.ivCategoryItem.setColorFilter(context.getResources().getColor(R.color.tint));
        holder.ivCategoryItem.setImageResource(category.getImageResourceId());
//        Picasso.with(context).load(category.getImageResourceId()).into(holder.ivCategoryItem);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;

        @InjectView(R.id.tvCategoryNameItem) protected TextView tvCategoryNameItem;
        @InjectView(R.id.ivCategoryItem) protected ImageView ivCategoryItem;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            this.context=context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getLayoutPosition();
            Intent intent=new Intent(context, RecipeActivity.class);
            intent.putExtra(RecipeActivity.RECIPE_ID_KEY, categories.get(position).getSearchValue());
            context.startActivity(intent);
        }
    }
}
