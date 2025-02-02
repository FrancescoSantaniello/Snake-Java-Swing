package fracesco.santaniello.gui.component;

import fracesco.santaniello.gui.LoseDialog;
import fracesco.santaniello.gui.GameWindow;
import fracesco.santaniello.gui.MainWindow;
import fracesco.santaniello.model.Cell;
import fracesco.santaniello.model.Direction;
import fracesco.santaniello.model.Food;
import fracesco.santaniello.model.Snake;
import fracesco.santaniello.util.ImageService;
import fracesco.santaniello.util.SoundService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalTime;

public class GamePanel extends JPanel implements ActionListener {

    private static Font font;
    private final Timer timer = new Timer(Snake.START_SPEED, this);
    private Graphics graphics;
    private Food food;
    private Direction direction;
    private short maxPoints;
    private boolean pause;
    private LocalTime startTime;
    private boolean wall = MainWindow.getInstance().isWallCheck();

    private static class InnerClass{
        private static final GamePanel instance = new GamePanel();
    }

    private GamePanel() {
        setBackground(new Color(0xAAD751));
        setSize(GameWindow.W, GameWindow.H);
        setPreferredSize(getSize());
        setBorder(BorderFactory.createLineBorder(Color.BLUE, Cell.SIZE / 5));
        if (font == null){
            try{
                font = Font.createFont(Font.TRUETYPE_FONT, new File("./source/font/font.ttf")).deriveFont(20f);
            }
            catch (Exception ex){
                font = new Font("Arial", Font.BOLD, 18);
            }
        }
    }

    public static GamePanel getInstance(){
        return InnerClass.instance;
    }

    public static Font getGameFont(){
        if (font == null){
            try{
                font = Font.createFont(Font.TRUETYPE_FONT, new File("./source/font/font.ttf")).deriveFont(20f);
            }
            catch (Exception ex){
                font = new Font("Arial", Font.BOLD, 18);
            }
        }
        return font;
    }

    public boolean isModWall(){
        return wall;
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
        graphics.setFont(font);
        graphics.drawString(String.valueOf(Snake.getInstance().getCells().size() - 1), (GameWindow.W / Cell.SIZE) / 2, Cell.SIZE);
    }

    private void drawSnake() {
        graphics.setColor(Snake.COLOR);
        Snake.getInstance().getCells().forEach(cell -> {
            graphics.fillRoundRect(cell.getX(), cell.getY(), Cell.SIZE, Cell.SIZE, Cell.SIZE / 2, Cell.SIZE / 2);
        });
    }

    private void drawPause(){
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString("Gioco in pausa", GameWindow.W / 2 - (Cell.SIZE * 3), GameWindow.H / 2);
    }

    public void drawFood() {
        if (ImageService.getInstance().getAppleImage() != null){
            graphics.drawImage(ImageService.getInstance().getAppleImage(), food.getCell().getX(), food.getCell().getY(), Cell.SIZE, Cell.SIZE,getBackground(), this);
        }
        else{
            graphics.setColor(Color.RED);
            graphics.fillOval(food.getCell().getX(), food.getCell().getY(), Cell.SIZE, Cell.SIZE);
        }
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
                SoundService.getInstance().playSoundEat();
            }
        }
        else {
            drawPause();
        }

        drawFood();
        drawSnake();
        drawPoints();
    }

    public short getMaxPoints(){
        return maxPoints;
    }

    public LocalTime getStartTime(){
        return startTime;
    }

    public void lose(){
        if (Snake.getInstance().getCells().size() - 1 > maxPoints)
            maxPoints = (short) (Snake.getInstance().getCells().size() - 1);

        timer.stop();
        SoundService.getInstance().playSoundGameOver();
        SoundService.getInstance().setPlayBackGround(false);
        GameWindow.getInstance().setVisible(false);
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
                        SoundService.getInstance().playSoundMove();
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP && direction != Direction.DOWN){
                        direction = Direction.DOWN;
                        SoundService.getInstance().playSoundMove();
                    }
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT && direction != Direction.LEFT){
                        direction = Direction.LEFT;
                        SoundService.getInstance().playSoundMove();
                    }
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT && direction != Direction.RIGHT){
                        direction = Direction.RIGHT;
                        SoundService.getInstance().playSoundMove();
                    }
                    break;
                case KeyEvent.VK_PLUS:
                    if (timer.getDelay() >= 10)
                        timer.setDelay(timer.getDelay() - 10);
                    break;
                case KeyEvent.VK_MINUS:
                    if (timer.getDelay() + 10 <= Integer.MAX_VALUE){
                        timer.setDelay(timer.getDelay() + 10);
                    }
                    break;
                case KeyEvent.VK_NUMPAD0:
                    timer.setDelay(timer.getInitialDelay());
                    break;
            }
        }
    }

    public void start(){
        if (!Snake.getInstance().getCells().isEmpty())
            Snake.getInstance().getCells().clear();

        Snake.getInstance().getCells().add(new Cell((short) (GameWindow.W / 2), (short) (GameWindow.H / 2)));
        food = Food.genFood();
        direction = Direction.NONE;
        timer.start();
        startTime = LocalTime.now();
        SoundService.getInstance().setPlayBackGround(true);
    }
}
