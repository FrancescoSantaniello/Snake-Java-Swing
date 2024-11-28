package fracesco.santaniello;

import fracesco.santaniello.gui.MainWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::getInstance);
    }
}
