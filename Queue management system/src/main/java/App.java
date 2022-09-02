import gui.SimulationFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame inputFrame = new SimulationFrame("Input");

        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setVisible(true);
    }
}
