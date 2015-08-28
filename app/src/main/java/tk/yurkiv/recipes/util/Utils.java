package tk.yurkiv.recipes.util;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Random;

/**
 * Created by yurkiv on 17.08.2015.
 */
public class Utils {

    private static Random random=new Random();

    public static String fixImageUrl(String url){
//        http://lh3.googleusercontent.com/CSmOH-M5ZZfOR8kzEu_1qyMs-3fUWSfkuE3MeqUs5I5UbByxCo_gamoqo2YXvCJQb3bAD5FyOTevv52tk1h2=s90
// to
//        http://lh3.googleusercontent.com/CSmOH-M5ZZfOR8kzEu_1qyMs-3fUWSfkuE3MeqUs5I5UbByxCo_gamoqo2YXvCJQb3bAD5FyOTevv52tk1h2=s900

        return url+"0";
    }

    public static int getLikes(int likes){
        return likes*100 + random.nextInt(99);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
