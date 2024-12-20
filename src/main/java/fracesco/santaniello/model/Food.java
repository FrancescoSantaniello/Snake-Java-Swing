package fracesco.santaniello.model;

import fracesco.santaniello.gui.GameWindow;

import java.util.Objects;
import java.util.Random;

public class Food {
    private Cell cell;

    public Food(){}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Food food)) return false;
        return Objects.equals(cell, food.cell);
    }

    public static Food genFood(){
        Random rand = new Random();
        Food food = new Food();
        food.setCell(new Cell());
        do{
            food.getCell().setY((short) (rand.nextInt(2, GameWindow.H / Cell.SIZE - 1) * Cell.SIZE));
            food.getCell().setX((short) (rand.nextInt(2, GameWindow.W / Cell.SIZE - 1) * Cell.SIZE));
        }
        while(Snake.getInstance().getCells().contains(food.getCell()));
        return food;
    }

    public Cell getCell(){
        return cell;
    }

    public void setCell(Cell cell){
        if (cell != null)
            this.cell = cell;
    }
}
