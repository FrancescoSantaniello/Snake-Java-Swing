package fracesco.santaniello.gui.component;

import fracesco.santaniello.gui.LoseDialog;
import fracesco.santaniello.gui.MainWindow;
import fracesco.santaniello.model.Cell;
import fracesco.santaniello.model.Direction;
import fracesco.santaniello.model.Food;
import fracesco.santaniello.model.Snake;
import fracesco.santaniello.util.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class GamePanel extends JPanel implements ActionListener {

    private Font pointsFont;
    private final Timer timer = new Timer(Math.round(1000f / Snake.SPEED), this);
    private Graphics graphics;
    private Food food;
    private Direction direction;
    private int maxPoints;
    private boolean pause;

    private static class InnerClass{
        private static final GamePanel instance = new GamePanel();
    }

    private GamePanel() {
        setBackground(new Color(0xAAD751));
        setSize(MainWindow.W, MainWindow.H);
        setPreferredSize(getSize());
        setBorder(BorderFactory.createLineBorder(Color.BLUE, Cell.SIZE / 5));

        try{
            pointsFont = Font.createFont(Font.TRUETYPE_FONT, new File("./source/font/font.ttf")).deriveFont(20f);
        }
        catch (Exception ex){
            pointsFont = new Font("Arial", Font.BOLD, 18);
        }

        start();
    }

    public static GamePanel getInstance(){
        return InnerClass.instance;
    }

    public Font getPointsFont(){
        return pointsFont;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.graphics = graphics;
        updateGame();
    }

    private void drawPoints(){
        graphics.setColor(Color.BLACK);
        graphics.setFont(pointsFont);
        graphics.drawString(Snake.getInstance().getCells().size() - 1 + "", (MainWindow.W / Cell.SIZE) / 2, Cell.SIZE);
    }

    private void drawSnake() {
        graphics.setColor(Snake.COLOR);
        Snake.getInstance().getCells().forEach(cell -> {
            graphics.fillOval(cell.getX(), cell.getY(), Cell.SIZE, Cell.SIZE);
        });
    }

    private void drawPause(){
        graphics.setColor(Color.BLACK);
        graphics.setFont(pointsFont);
        graphics.drawString("Gioco in pausa", MainWindow.W / 2 - (Cell.SIZE * 3), MainWindow.H / 2);
    }

    public void drawFood() {
        graphics.setColor(food.getFoodType().getColor());
        graphics.fillOval(food.getCell().getX(), food.getCell().getY(), Cell.SIZE, Cell.SIZE);
    }

    private void updateGame(){
        if (!pause){
            boolean eat = Snake.getInstance().getCells().contains(food.getCell());
            if (direction != Direction.NONE && !Snake.getInstance().advance(direction, eat)){
                lose();
                return;
            }
            if (eat){
                food = Food.genFood();
                SoundPlayer.getInstance().playSoundEat();
            }
        }
        else {
            drawPause();
        }

        drawFood();
        drawSnake();
        drawPoints();
    }

    public int getMaxPoints(){
        return maxPoints;
    }

    public void lose(){
        if (Snake.getInstance().getCells().size() - 1 > maxPoints)
            maxPoints = Snake.getInstance().getCells().size() - 1;
        SoundPlayer.getInstance().playSoundGameOver();
        timer.stop();
        LoseDialog.getInstance().setVisible(true);
    }

    public void move(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_P)
            pause = !pause;
        if (!pause){
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN && direction != Direction.UP){
                        direction = Direction.UP;
                        SoundPlayer.getInstance().playSoundMove();
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP && direction != Direction.DOWN){
                        direction = Direction.DOWN;
                        SoundPlayer.getInstance().playSoundMove();
                    }
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT && direction != Direction.LEFT){
                        direction = Direction.LEFT;
                        SoundPlayer.getInstance().playSoundMove();
                    }
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT && direction != Direction.RIGHT){
                        direction = Direction.RIGHT;
                        SoundPlayer.getInstance().playSoundMove();
                    }
                    break;
            }
        }
    }


    public void start(){
        Snake.getInstance().getCells().add(new Cell((short) (MainWindow.W / 2), (short) (MainWindow.H / 2)));
        food = Food.genFood();
        direction = Direction.NONE;
        timer.start();
    }
}
