package fracesco.santaniello.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fracesco.santaniello.gui.component.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private final JCheckBox wallCheckBox = new JCheckBox("Muri di gomma");

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
        setSize(400,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponent();
    }

    public static MainWindow getInstance(){
        return InnerClass.instance;
    }

    public boolean isWallCheck(){
        return !wallCheckBox.isSelected();
    }

    public void initComponent(){
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Snake");

        label.setFont(GamePanel.getGameFont());

        JButton buttonStart = new JButton("Gioca");

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.getInstance().setVisible(true);
                GamePanel.getInstance().start();
                dispose();
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(7,7,7,7);

        panel.add(label, constraints);

        constraints.gridy = 1;
        panel.add(wallCheckBox, constraints);

        constraints.gridy = 2;
        panel.add(buttonStart, constraints);

        add(panel);
    }
}