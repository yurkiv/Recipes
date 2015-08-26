
package tk.yurkiv.recipes.model;

import com.google.gson.annotations.Expose;

public class Unit {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String abbreviation;
    @Expose
    private String plural;
    @Expose
    private String pluralAbbreviation;
    @Expose
    private boolean decimal;

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
     *     The abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * 
     * @param abbreviation
     *     The abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * 
     * @return
     *     The plural
     */
    public String getPlural() {
        return plural;
    }

    /**
     * 
     * @param plural
     *     The plural
     */
    public void setPlural(String plural) {
        this.plural = plural;
    }

    /**
     * 
     * @return
     *     The pluralAbbreviation
     */
    public String getPluralAbbreviation() {
        return pluralAbbreviation;
    }

    /**
     * 
     * @param pluralAbbreviation
     *     The pluralAbbreviation
     */
    public void setPluralAbbreviation(String pluralAbbreviation) {
        this.pluralAbbreviation = pluralAbbreviation;
    }

    /**
     * 
     * @return
     *     The decimal
     */
    public boolean isDecimal() {
        return decimal;
    }

    /**
     * 
     * @param decimal
     *     The decimal
     */
    public void setDecimal(boolean decimal) {
        this.decimal = decimal;
    }

}
