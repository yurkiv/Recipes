
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Category {

    @Expose
    private List<Course> course = new ArrayList<Course>();

    /**
     * 
     * @return
     *     The course
     */
    public List<Course> getCourse() {
        return course;
    }

    /**
     * 
     * @param course
     *     The course
     */
    public void setCourse(List<Course> course) {
        this.course = course;
    }

}
