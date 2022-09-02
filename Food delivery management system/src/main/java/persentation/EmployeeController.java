package persentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController implements ActionListener {
    private EmployeeView employeeView;

    public EmployeeController(EmployeeView employeeView){
        this.employeeView = employeeView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("LOG OUT".equals(command)) {
            this.employeeView.dispose();
            LoginView loginView = new LoginView("Login");
            loginView.setVisible(true);
            loginView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }
}
