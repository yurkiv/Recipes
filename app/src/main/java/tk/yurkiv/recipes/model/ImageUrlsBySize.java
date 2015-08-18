
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUrlsBySize {

    @SerializedName("90")
    @Expose
    private String _90;
    @SerializedName("360")
    @Expose
    private String _360;

    /**
     * 
     * @return
     *     The _90
     */
    public String get90() {
        return _90;
    }

    /**
     * 
     * @param _90
     *     The 90
     */
    public void set90(String _90) {
        this._90 = _90;
    }

    /**
     * 
     * @return
     *     The _360
     */
    public String get360() {
        return _360;
    }

    /**
     * 
     * @param _360
     *     The 360
     */
    public void set360(String _360) {
        this._360 = _360;
    }

}
