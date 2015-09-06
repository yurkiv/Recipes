package tk.yurkiv.recipes.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import tk.yurkiv.recipes.model.YummlyRecipe;
import tk.yurkiv.recipes.model.YummlyRecipesListResponse;

/**
 * Created by yurkiv on 17.08.2015.
 */
public interface YummlyService {

    @GET("/recipes")
    public void getRecipes(@Query("q") String q,
                           @Query("maxResult") int maxResult,
                           @Query("start") int start,
                           @Query("allowedAllergy[]") String allowedAllergy,
                           @Query("allowedCuisine[]") String allowedCuisine,
                           @Query("allowedCourse[]") String allowedCourse,
                           @Query("allowedHoliday[]") String allowedHoliday,
                           @Query("allowedDiet[]") String allowedDiet,
                           @Query("maxTotalTimeInSeconds") String maxTotalTimeInSeconds,
                           @Query("nutrition.ENERC_KCAL.max") String maxEnergy,
                           Callback<YummlyRecipesListResponse> cb);

    @GET("/recipe/{id}")
    public void getRecipe(@Path("id") String id,
                          Callback<YummlyRecipe> cb);

}
