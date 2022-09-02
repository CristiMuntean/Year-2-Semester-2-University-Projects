import gui.View;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new View("Polynomial calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
