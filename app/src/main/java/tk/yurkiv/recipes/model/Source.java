
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

public class Source {

    @Expose
    private String sourceDisplayName;
    @Expose
    private String sourceSiteUrl;
    @Expose
    private String sourceRecipeUrl;

    /**
     * 
     * @return
     *     The sourceDisplayName
     */
    public String getSourceDisplayName() {
        return sourceDisplayName;
    }

    /**
     * 
     * @param sourceDisplayName
     *     The sourceDisplayName
     */
    public void setSourceDisplayName(String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
    }

    /**
     * 
     * @return
     *     The sourceSiteUrl
     */
    public String getSourceSiteUrl() {
        return sourceSiteUrl;
    }

    /**
     * 
     * @param sourceSiteUrl
     *     The sourceSiteUrl
     */
    public void setSourceSiteUrl(String sourceSiteUrl) {
        this.sourceSiteUrl = sourceSiteUrl;
    }

    /**
     * 
     * @return
     *     The sourceRecipeUrl
     */
    public String getSourceRecipeUrl() {
        return sourceRecipeUrl;
    }

    /**
     * 
     * @param sourceRecipeUrl
     *     The sourceRecipeUrl
     */
    public void setSourceRecipeUrl(String sourceRecipeUrl) {
        this.sourceRecipeUrl = sourceRecipeUrl;
    }

}
