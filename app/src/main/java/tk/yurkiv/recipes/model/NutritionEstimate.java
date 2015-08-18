
package tk.yurkiv.recipes.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

public class NutritionEstimate {

    @Expose
    private String attribute;
    @Expose
    private String description;
    @Expose
    private double value;
    @Expose
    private Unit unit;

    /**
     * 
     * @return
     *     The attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * 
     * @param attribute
     *     The attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The value
     */
    public double getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit
     *     The unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
