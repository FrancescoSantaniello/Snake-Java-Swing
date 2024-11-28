package fracesco.santaniello.model;

import fracesco.santaniello.gui.MainWindow;

import java.util.Objects;
import java.util.Random;

public class Food {
    private Cell cell;
    private FoodType foodType;

    public Food(){}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Food food)) return false;
        return Objects.equals(cell, food.cell) && foodType == food.foodType;
    }

    public static Food genFood(){
        Random rand = new Random();
        Food food = new Food();
        food.setFoodType(FoodType.values()[rand.nextInt(FoodType.values().length)]);
        food.setCell(new Cell());
        do{
            food.getCell().setY((short) (rand.nextInt(2, MainWindow.H / Cell.SIZE - 1) * Cell.SIZE));
            food.getCell().setX((short) (rand.nextInt(2, MainWindow.W / Cell.SIZE - 1) * Cell.SIZE));
        }
        while(Snake.getInstance().getCells().contains(food.getCell()));
        return food;
    }

    public Cell getCell(){
        return cell;
    }

    public FoodType getFoodType(){
        return foodType;
    }

    public void setCell(Cell cell){
        if (cell != null)
            this.cell = cell;
    }

    public void setFoodType(FoodType foodType){
        if (foodType != null)
            this.foodType = foodType;
    }
}
