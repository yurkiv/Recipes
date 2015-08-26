package tk.yurkiv.recipes.model;

/**
 * Created by yurkiv on 19.08.2015.
 */
public class Ingredient {
    private String name;
    private boolean isSelected;

    public Ingredient(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
