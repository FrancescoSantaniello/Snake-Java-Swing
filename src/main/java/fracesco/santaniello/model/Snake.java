package fracesco.santaniello.model;

import fracesco.santaniello.gui.MainWindow;
import fracesco.santaniello.gui.component.GamePanel;
import fracesco.santaniello.util.SoundService;

import java.awt.Color;
import java.util.LinkedHashSet;

public class Snake {
    public static final Color COLOR = new Color(0x4370E4);
    public static final int START_SPEED = (int)(Math.round(1000f / ((MainWindow.W * MainWindow.H) / Math.pow(Cell.SIZE,3.15))));
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
        if (!GamePanel.getInstance().isModWall()){
            if (newCell.getY() >= MainWindow.H){
                newCell.setY((short)0);
                SoundService.getInstance().playSoundTeleport();
            }
            else if (newCell.getY() < 0){
                newCell.setY((short) (MainWindow.H - Cell.SIZE));
                SoundService.getInstance().playSoundTeleport();
            }
            if (newCell.getX() >= MainWindow.W){
                newCell.setX((short)0);
                SoundService.getInstance().playSoundTeleport();
            }
            else if (newCell.getX() < 0){
                newCell.setX((short) (MainWindow.W - Cell.SIZE));
                SoundService.getInstance().playSoundTeleport();
            }
        }
        else if (newCell.getY() >= MainWindow.H || newCell.getY() < 0 || newCell.getX() >= MainWindow.W || newCell.getX() < 0){
            return false;
        }

        if(cells.add(newCell)){
            if (!eaten)
                cells.removeFirst();
        }
        else{
            return false;
        }
        return true;
    }
}
