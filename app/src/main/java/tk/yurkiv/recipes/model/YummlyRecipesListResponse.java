
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class YummlyRecipesListResponse {


    @Expose
    private List<Match> matches = new ArrayList<>();

    public List<Match> getMatches() {
        return matches;
    }
}
