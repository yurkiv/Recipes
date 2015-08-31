
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Category {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String type;
    @Expose
    private String description;
    @Expose
    private String searchValue;
    @Expose
    private List<String> localesAvailableIn = new ArrayList<String>();
    @Expose
    private String imageId;

    @Expose
    private String longDescription;

    private int imageResourceId;

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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
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
     *     The searchValue
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * 
     * @param searchValue
     *     The searchValue
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * 
     * @return
     *     The localesAvailableIn
     */
    public List<String> getLocalesAvailableIn() {
        return localesAvailableIn;
    }

    /**
     * 
     * @param localesAvailableIn
     *     The localesAvailableIn
     */
    public void setLocalesAvailableIn(List<String> localesAvailableIn) {
        this.localesAvailableIn = localesAvailableIn;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getLongDescription() {
        return longDescription;
    }
}
