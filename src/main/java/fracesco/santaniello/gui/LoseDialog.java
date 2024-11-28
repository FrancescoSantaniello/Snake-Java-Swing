package fracesco.santaniello.gui;

import fracesco.santaniello.gui.component.GamePanel;
import fracesco.santaniello.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoseDialog extends JDialog {

    private final JLabel puntiLabel = new JLabel("");
    private final JLabel recordLabel = new JLabel("");

    private static class InnerClass{
        private static final LoseDialog instance = new LoseDialog();
    }

    private LoseDialog(){
        setModal(true);
        setTitle("Hai perso!");
        setSize(400,200);
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
            puntiLabel.setText("Punteggio " + (Snake.getInstance().getCells().size() - 1));
            recordLabel.setText("Record " + GamePanel.getInstance().getMaxPoints());
        }
        super.setVisible(b);
    }

    private void initComponent(){
        JButton exit = new JButton("Esci"),
                newGame = new JButton("Nuova partita");
        JPanel panel = new JPanel(new GridBagLayout()),
                labelPanel = new JPanel(new GridBagLayout()),
                buttonPanel = new JPanel();

        puntiLabel.setFont(GamePanel.getInstance().getPointsFont());
        recordLabel.setFont(GamePanel.getInstance().getPointsFont());

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        newGame.addActionListener(new ActionListener() {
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
        labelPanel.add(recordLabel, constraints);

        buttonPanel.add(exit, BorderLayout.WEST);
        buttonPanel.add(newGame, BorderLayout.EAST);

        panel.add(labelPanel, constraints);

        constraints.gridy = 2;
        panel.add(buttonPanel, constraints);
        add(panel);
    }
}
