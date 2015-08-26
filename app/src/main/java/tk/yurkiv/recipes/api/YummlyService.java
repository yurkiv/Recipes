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
                           @Query("allowedIngredient[]") String allowedIngredient,
                           @Query("allowedAllergy[]") String allowedAllergy,
                           @Query("allowedCuisine[]") String allowedCuisine,
                           @Query("allowedCourse[]") String allowedCourse,
                           @Query("allowedHoliday[]") String allowedHoliday,
                           Callback<YummlyRecipesListResponse> cb);

    @GET("/recipes")
    public void getRecipesByQuery(@Query("q") String q,
                                  @Query("maxResult") int maxResult,
                                  @Query("start") int start,
                                   Callback<YummlyRecipesListResponse> cb);

//    @GET("/recipes")
//    public void getRecipesByIngredient(@Query("allowedIngredient[]") String allowedIngredient,
//                                       @Query("maxResult") String maxResult,
//                                       @Query("start") String start,
//                                       Callback<YummlyRecipesListResponse> cb);

    @GET("/recipes")
    public void getRecipesByAllergy(@Query("allowedAllergy[]") String allowedAllergy,
                                    @Query("maxResult") int maxResult,
                                    @Query("start") int start,
                                    Callback<YummlyRecipesListResponse> cb);

    @GET("/recipes")
    public void getRecipesByCuisine(@Query("allowedCuisine[]") String allowedCuisine,
                                    @Query("maxResult") int maxResult,
                                    @Query("start") int start,
                                    Callback<YummlyRecipesListResponse> cb);

    @GET("/recipes")
    public void getRecipesByCourse(@Query("allowedCourse[]") String allowedCourse,
                                   @Query("maxResult") int maxResult,
                                   @Query("start") int start,
                                   Callback<YummlyRecipesListResponse> cb);

    @GET("/recipes")
    public void getRecipesByHoliday(@Query("allowedHoliday[]") String allowedHoliday,
                                    @Query("maxResult") int maxResult,
                                    @Query("start") int start,
                                    Callback<YummlyRecipesListResponse> cb);

    @GET("/recipe/{id}")
    public void getRecipe(@Path("id") String id,
                          Callback<YummlyRecipe> cb);

}
