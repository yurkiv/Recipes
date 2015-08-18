
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match {

    @Expose
    private String sourceDisplayName;
    @Expose
    private String id;
    @Expose
    private List<String> smallImageUrls = new ArrayList<String>();
    @Expose
    private String recipeName;
    @Expose
    private Integer totalTimeInSeconds;
    @Expose
    private Integer rating;

    private Random random=new Random();

    public String getSourceDisplayName() {
        return sourceDisplayName;
    }

    public String getId() {
        return id;
    }

    public List<String> getSmallImageUrls() {
        return smallImageUrls;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Integer getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    public Integer getRating() {
        return rating;
    }

    public String getDuration(){
        int hours = getTotalTimeInSeconds() / 3600;
        int minutes = (getTotalTimeInSeconds() % 3600) / 60;
        if (hours==0){
            return minutes + "m";
        } else if (minutes==0){
            return hours + "h";
        } else {
            return hours + "h " + minutes + "m";
        }
    }

    public int getLikes(){
        return getRating()*100 + random.nextInt(99);
    }

    @Override
    public String toString() {
        return "Match{" +
                "sourceDisplayName='" + sourceDisplayName + '\'' +
                ", id='" + id + '\'' +
                ", smallImageUrls=" + smallImageUrls +
                ", recipeName='" + recipeName + '\'' +
                ", totalTimeInSeconds=" + totalTimeInSeconds +
                ", rating=" + rating +
                '}';
    }
}
