import presentation.View;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world");
        View view = new View("Order Management System");
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.setVisible(true);
    }
}
