package tk.yurkiv.recipes.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tk.yurkiv.recipes.model.Category;

/**
 * Created by yurkiv on 17.08.2015.
 */
public class Utils {

    private static final Random RANDOM=new Random();

    public static String fixImageUrl(String url){
//        http://lh3.googleusercontent.com/CSmOH-M5ZZfOR8kzEu_1qyMs-3fUWSfkuE3MeqUs5I5UbByxCo_gamoqo2YXvCJQb3bAD5FyOTevv52tk1h2=s90
// to
//        http://lh3.googleusercontent.com/CSmOH-M5ZZfOR8kzEu_1qyMs-3fUWSfkuE3MeqUs5I5UbByxCo_gamoqo2YXvCJQb3bAD5FyOTevv52tk1h2=s900

        return url+"0";
    }

    public static int getLikes(int likes){
        return likes*100 + RANDOM.nextInt(99);
    }

    public static  GridLayoutManager getGridLayoutManager(Context context) {
        final boolean isTablet=Utils.isTablet(context);
        int spanCount=2;
        if (isTablet){
            spanCount=6;
        }

        GridLayoutManager gridLayoutManager=new GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (isTablet){
                    int index = position % 5;
                    int span = 3;
                    switch (index) {
                        case 0:
                            span = 2;
                            break;
                        case 1:
                            span = 2;
                            break;
                        case 2:
                            span = 2;
                            break;
                        case 3:
                            span = 3;
                            break;
                        case 4:
                            span = 3;
                            break;
                    }
                    return span;
                } else {
                    return ((position-1) % 3 == 0 ? 2 : 1); //for phone
                }
            }
        });
        return gridLayoutManager;
    }


    private static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static List<Category> getCategoryRes(Context context, String res) {
        List<Category> categories=new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream ims = assetManager.open(res);

            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);
            Type listType = new TypeToken<List<Category>>(){}.getType();

            categories= (List<Category>) gson.fromJson(reader, listType);

            String packageName = context.getPackageName();
            for(Category category : categories){
                category.setImageResourceId(context.getResources().getIdentifier(category.getImageId(), "drawable", packageName));
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
