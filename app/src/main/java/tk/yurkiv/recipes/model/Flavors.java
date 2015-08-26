
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

public class Flavors {

    @Expose
    private float Piquant;
    @Expose
    private float Meaty;
    @Expose
    private float Bitter;
    @Expose
    private float Sweet;
    @Expose
    private float Sour;
    @Expose
    private float Salty;

    public float getPiquant() {
        return Piquant;
    }

    public float getMeaty() {
        return Meaty;
    }

    public float getBitter() {
        return Bitter;
    }

    public float getSweet() {
        return Sweet;
    }

    public float getSour() {
        return Sour;
    }

    public float getSalty() {
        return Salty;
    }
}
