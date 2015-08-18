
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

public class Flavors {

    @Expose
    private double Piquant;
    @Expose
    private double Meaty;
    @Expose
    private double Bitter;
    @Expose
    private double Sweet;
    @Expose
    private double Sour;
    @Expose
    private double Salty;

    /**
     * 
     * @return
     *     The Piquant
     */
    public double getPiquant() {
        return Piquant;
    }

    /**
     * 
     * @param Piquant
     *     The Piquant
     */
    public void setPiquant(double Piquant) {
        this.Piquant = Piquant;
    }

    /**
     * 
     * @return
     *     The Meaty
     */
    public double getMeaty() {
        return Meaty;
    }

    /**
     * 
     * @param Meaty
     *     The Meaty
     */
    public void setMeaty(double Meaty) {
        this.Meaty = Meaty;
    }

    /**
     * 
     * @return
     *     The Bitter
     */
    public double getBitter() {
        return Bitter;
    }

    /**
     * 
     * @param Bitter
     *     The Bitter
     */
    public void setBitter(double Bitter) {
        this.Bitter = Bitter;
    }

    /**
     * 
     * @return
     *     The Sweet
     */
    public double getSweet() {
        return Sweet;
    }

    /**
     * 
     * @param Sweet
     *     The Sweet
     */
    public void setSweet(double Sweet) {
        this.Sweet = Sweet;
    }

    /**
     * 
     * @return
     *     The Sour
     */
    public double getSour() {
        return Sour;
    }

    /**
     * 
     * @param Sour
     *     The Sour
     */
    public void setSour(double Sour) {
        this.Sour = Sour;
    }

    /**
     * 
     * @return
     *     The Salty
     */
    public double getSalty() {
        return Salty;
    }

    /**
     * 
     * @param Salty
     *     The Salty
     */
    public void setSalty(double Salty) {
        this.Salty = Salty;
    }

}
