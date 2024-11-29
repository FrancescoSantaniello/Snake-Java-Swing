package fracesco.santaniello.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fracesco.santaniello.gui.component.GamePanel;
import fracesco.santaniello.model.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

    public static final short W = 600;
    public static final short H = 600;

    private static class InnerClass{
        private static final MainWindow instance = new MainWindow();
    }

    static{
        try{
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private MainWindow(){
        super("Snake");
        if (W % Cell.SIZE != 0 || H % Cell.SIZE != 0)
            throw new IllegalArgumentException("Dimenensioni non valide");
        setSize(W + Cell.SIZE * 2, H + Cell.SIZE * 4);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        add(GamePanel.getInstance(), new GridBagConstraints());
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                GamePanel.getInstance().move(e);
            }
        });
        setVisible(true);
    }

    public static MainWindow getInstance(){
        return InnerClass.instance;
    }
}