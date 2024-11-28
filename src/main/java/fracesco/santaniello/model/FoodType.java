package fracesco.santaniello.model;

import java.awt.*;

public enum FoodType {
    BANANA(Color.YELLOW),
    MELA(Color.RED),
    CETRIOLO(Color.WHITE);

    FoodType(Color color){
        this.color = color;
    }

    private final Color color;

    public Color getColor(){
        return color;
    }
}
