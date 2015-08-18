
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Attributes {

    @Expose
    private List<String> course = new ArrayList<String>();

    /**
     * 
     * @return
     *     The course
     */
    public List<String> getCourse() {
        return course;
    }

    /**
     * 
     * @param course
     *     The course
     */
    public void setCourse(List<String> course) {
        this.course = course;
    }

}
