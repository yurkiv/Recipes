
package tk.yurkiv.recipes.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

public class YummlyRecipe {

    @Expose
    private Object yield;
    @Expose
    private List<NutritionEstimate> nutritionEstimates = new ArrayList<NutritionEstimate>();
    @Expose
    private String totalTime;
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @Expose
    private String name;
    @Expose
    private Source source;
    @Expose
    private String id;
    @Expose
    private List<String> ingredientLines = new ArrayList<String>();
    @Expose
    private Attribution attribution;
    @Expose
    private int numberOfServings;
    @Expose
    private int totalTimeInSeconds;
    @Expose
    private Attributes attributes;
    @Expose
    private Flavors flavors;
    @Expose
    private int rating;

    /**
     * 
     * @return
     *     The yield
     */
    public Object getYield() {
        return yield;
    }

    /**
     * 
     * @param yield
     *     The yield
     */
    public void setYield(Object yield) {
        this.yield = yield;
    }

    /**
     * 
     * @return
     *     The nutritionEstimates
     */
    public List<NutritionEstimate> getNutritionEstimates() {
        return nutritionEstimates;
    }

    /**
     * 
     * @param nutritionEstimates
     *     The nutritionEstimates
     */
    public void setNutritionEstimates(List<NutritionEstimate> nutritionEstimates) {
        this.nutritionEstimates = nutritionEstimates;
    }

    /**
     * 
     * @return
     *     The totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * 
     * @param totalTime
     *     The totalTime
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * 
     * @return
     *     The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The source
     */
    public Source getSource() {
        return source;
    }

    /**
     * 
     * @param source
     *     The source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The ingredientLines
     */
    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    /**
     * 
     * @param ingredientLines
     *     The ingredientLines
     */
    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    /**
     * 
     * @return
     *     The attribution
     */
    public Attribution getAttribution() {
        return attribution;
    }

    /**
     * 
     * @param attribution
     *     The attribution
     */
    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    /**
     * 
     * @return
     *     The numberOfServings
     */
    public int getNumberOfServings() {
        return numberOfServings;
    }

    /**
     * 
     * @param numberOfServings
     *     The numberOfServings
     */
    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    /**
     * 
     * @return
     *     The totalTimeInSeconds
     */
    public int getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    /**
     * 
     * @param totalTimeInSeconds
     *     The totalTimeInSeconds
     */
    public void setTotalTimeInSeconds(int totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
    }

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * @return
     *     The flavors
     */
    public Flavors getFlavors() {
        return flavors;
    }

    /**
     * 
     * @param flavors
     *     The flavors
     */
    public void setFlavors(Flavors flavors) {
        this.flavors = flavors;
    }

    /**
     * 
     * @return
     *     The rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

}
