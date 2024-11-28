package fracesco.santaniello.model;

import java.util.Objects;

public class Cell {
    public static final byte SIZE = 25;
    private short x;
    private short y;

    public Cell(){}

    public Cell(short x, short y){
        setX(x);
        setY(y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell cell)) return false;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public short getX(){
        return x;
    }

    public short getY(){
        return y;
    }

    public void setX(short x){
        this.x = x;
    }

    public void setY(short y){
        this.y = y;
    }
}
