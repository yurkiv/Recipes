package tk.yurkiv.recipes.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Match;
import tk.yurkiv.recipes.ui.activities.RecipeActivity;

/**
 * Created by yurkiv on 17.08.2015.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private Context context;
    private List<Match> matches;

    public RecipesAdapter(Context context, List<Match> matches) {
        this.context = context;
        this.matches = matches;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cvRoot.setVisibility(View.GONE);
        Match match=matches.get(position);
        match.setLikes();
        holder.tvRecipeNameItem.setText(match.getRecipeName());
        holder.tvRatingItem.setText(match.getLikes());
        holder.tvTimeItem.setText(match.getDuration());
        holder.ivRecipeItem.setColorFilter(context.getResources().getColor(R.color.tint));
        Picasso.with(context).load(match.getSmallImageUrls().get(0) + "0").into(holder.ivRecipeItem, new Callback() {
            @Override
            public void onSuccess() {
                holder.cvRoot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });


    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;
        @InjectView(R.id.cvRoot) protected CardView cvRoot;
        @InjectView(R.id.tvRecipeNameItem) protected TextView tvRecipeNameItem;
        @InjectView(R.id.tvRatingItem) protected TextView tvRatingItem;
        @InjectView(R.id.tvTimeItem) protected TextView tvTimeItem;
        @InjectView(R.id.ivRecipeItem) protected SelectableRoundedImageView ivRecipeItem;

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
            intent.putExtra(RecipeActivity.RECIPE_ID_KEY, matches.get(position).getId());
            intent.putExtra(RecipeActivity.RECIPE_RATING_KEY, matches.get(position).getLikes());
            context.startActivity(intent);
        }
    }
}
