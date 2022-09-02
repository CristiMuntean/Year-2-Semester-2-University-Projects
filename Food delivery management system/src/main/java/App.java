import persentation.LoginView;
import javax.swing.*;

public class App {

    public static void main(String[] args) {
        LoginView loginView = new LoginView("Login");
        loginView.setVisible(true);
        loginView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
