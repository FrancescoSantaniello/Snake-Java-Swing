package fracesco.santaniello.model;

public enum Direction {
    UP(new byte[]{0,-1}),
    DOWN(new byte[]{0,1}),
    LEFT(new byte[]{-1,0}),
    RIGHT(new byte[]{1,0}),
    NONE(new byte[]{0,0});

    private final byte[] directionArray;

    public byte[] getDirectionArray(){
        return directionArray;
    }

    Direction(byte[] directionArray){
        this.directionArray = directionArray;
    }
}
