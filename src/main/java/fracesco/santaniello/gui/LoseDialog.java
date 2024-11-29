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
        setLocationRelativeTo(MainWindow.getInstance());
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
                newGameButton = new JButton("Nuova partita");
        JPanel panel = new JPanel(new GridBagLayout()),
                labelPanel = new JPanel(new GridBagLayout()),
                buttonPanel = new JPanel();

        puntiLabel.setFont(GamePanel.getInstance().getFont());
        recordLabel.setFont(GamePanel.getInstance().getFont());
        timeLabel.setFont(GamePanel.getInstance().getFont());

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Snake.getInstance().getCells().clear();
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

        buttonPanel.add(exitButton, BorderLayout.WEST);
        buttonPanel.add(newGameButton, BorderLayout.EAST);

        panel.add(labelPanel, constraints);

        constraints.gridy = 3;
        panel.add(buttonPanel, constraints);
        add(panel);
    }
}
