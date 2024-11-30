package fracesco.santaniello.gui;

import fracesco.santaniello.gui.component.GamePanel;
import fracesco.santaniello.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;

public class LoseDialog extends JDialog {

    private final JLabel puntiLabel = new JLabel("");
    private final JLabel recordLabel = new JLabel("");
    private final JLabel timeLabel = new JLabel("");

    private static class InnerClass{
        private static final LoseDialog instance = new LoseDialog();
    }

    private LoseDialog(){
        setModal(true);
        setTitle("Hai perso!");
        setSize(350,250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(GameWindow.getInstance());
        initComponent();
    }

    public static LoseDialog getInstance(){
        return InnerClass.instance;
    }

    @Override
    public void setVisible(boolean b) {
        if (b){
            Duration duration = Duration.between(GamePanel.getInstance().getStartTime(), LocalTime.now());
            timeLabel.setText("Tempo di gioco %5d %5d %5d".formatted(duration.toHours(), duration.toMinutes() % 60, duration.getSeconds() % 60));
            puntiLabel.setText("Punteggio " + (Snake.getInstance().getCells().size() - 1));
            recordLabel.setText("Record " + GamePanel.getInstance().getMaxPoints());
        }
        super.setVisible(b);
    }

    private void initComponent(){
        JButton exitButton = new JButton("Esci"),
                newGameButton = new JButton("Nuova partita"),
                menuButton = new JButton("Torna al menu");
        JPanel panel = new JPanel(new GridBagLayout()),
                labelPanel = new JPanel(new GridBagLayout()),
                buttonPanel = new JPanel();

        puntiLabel.setFont(GamePanel.getGameFont());
        recordLabel.setFont(GamePanel.getGameFont());
        timeLabel.setFont(GamePanel.getGameFont());

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.getInstance().setVisible(true);
                dispose();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.getInstance().setVisible(true);
                GamePanel.getInstance().start();
                dispose();
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5,5,5,5);

        labelPanel.add(puntiLabel, constraints);

        constraints.gridy = 1;
        labelPanel.add(timeLabel, constraints);

        constraints.gridy = 2;
        labelPanel.add(recordLabel, constraints);

        buttonPanel.add(menuButton, BorderLayout.WEST);
        buttonPanel.add(exitButton, BorderLayout.CENTER);
        buttonPanel.add(newGameButton, BorderLayout.EAST);

        panel.add(labelPanel, constraints);

        constraints.gridy = 3;
        panel.add(buttonPanel, constraints);
        add(panel);
    }
}
