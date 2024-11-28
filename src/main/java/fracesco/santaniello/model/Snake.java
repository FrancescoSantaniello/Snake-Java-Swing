package fracesco.santaniello.model;

import fracesco.santaniello.gui.MainWindow;

import java.awt.Color;
import java.util.LinkedHashSet;

public class Snake {
    public static final Color COLOR = new Color(0x4370E4);
    public static final byte SPEED = (byte)((MainWindow.W * MainWindow.H) / Math.pow(Cell.SIZE,3.15));
    private final LinkedHashSet<Cell> cells = new LinkedHashSet<>();


    private static class InnerClass{
        private static final Snake instance = new Snake();
    }

    private Snake(){}

    public static Snake getInstance(){
        return InnerClass.instance;
    }

    public LinkedHashSet<Cell> getCells(){
        return cells;
    }

    public boolean advance(Direction direction, boolean eaten){
        if (direction == null)
            throw new IllegalArgumentException("Direzione non valida");
        Cell head = cells.getLast();
        Cell newCell = new Cell((short) (head.getX() + direction.getDirectionArray()[0] * Cell.SIZE), (short) (head.getY() + direction.getDirectionArray()[1] * Cell.SIZE));
        if(cells.add(newCell) && newCell.getY() < MainWindow.H && newCell.getY() >= 0 && newCell.getX() < MainWindow.W && newCell.getX() >= 0){
            if (!eaten)
                cells.removeFirst();
        }
        else{
                return false;
        }
        return true;
    }
}
