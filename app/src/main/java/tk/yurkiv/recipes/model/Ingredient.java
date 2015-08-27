package tk.yurkiv.recipes.model;

/**
 * Created by yurkiv on 19.08.2015.
 */
public class Ingredient {
    private String name;
    private boolean selected;

    public Ingredient(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
