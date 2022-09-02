package persentation;

import businessLayer.DeliveryService;
import dataAccess.Serializator;
import dataAccess.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginController implements ActionListener {
    private LoginView loginView;
    private boolean showPassword;
    private DeliveryService deliveryService;
    private final String ACCOUNTS_FILE = "Accounts.txt";
    private EmployeeView employeeView;

    public LoginController(LoginView loginView){
        this.loginView = loginView;
        this.showPassword = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "SHOW PASSWORD"->{
                this.showPassword = !this.showPassword;
                if(this.showPassword)this.loginView.getPasswordField().setEchoChar((char)0);
                else this.loginView.getPasswordField().setEchoChar('*');
            }
            case "LOGIN" ->{
                String username = this.loginView.getUsernameTextField().getText();
                String password = String.valueOf(this.loginView.getPasswordField().getPassword());
                this.deliveryService = new DeliveryService();
                this.employeeView = new EmployeeView("Employee window",this.deliveryService);
                Serializator serializator = new Serializator(this.deliveryService);
                User user = serializator.login(username,password,this.ACCOUNTS_FILE);
                if(user!=null){
                    deliveryService.setUser(user);
                    if(user.getAccess().equals("Client")){
                        this.loginView.dispose();

                        this.deliveryService.loadInfo();
                        this.employeeView.prepareGui();
                        this.employeeView.setVisible(true);
                        this.employeeView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                        this.deliveryService.addObserver(this.employeeView);
                        ClientView clientView = new ClientView("Client operations",this.deliveryService,this.employeeView);
                        clientView.setVisible(true);
                        clientView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                    }
                    else if(user.getAccess().equals("Admin")){
                        this.loginView.dispose();
                        AdminView adminView = new AdminView("Admin operations",this.deliveryService);
                        adminView.setVisible(true);
                        adminView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    }
                    else if(user.getAccess().equals("Employee")){
                        this.loginView.dispose();
                        this.deliveryService.loadInfo();
                        this.employeeView.prepareActionGui();
                        this.employeeView.setVisible(true);
                        this.employeeView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    }
                }
                else this.createOptionPane("Invalid username/password");
            }
            case "REGISTER"->{
                String username = this.loginView.getUsernameTextField().getText();
                String password = String.valueOf(this.loginView.getPasswordField().getPassword());
                this.deliveryService = new DeliveryService();
                this.employeeView = new EmployeeView("Employee window",this.deliveryService);
                Serializator serializator = new Serializator(this.deliveryService);
                if(serializator.register(username,password, this.ACCOUNTS_FILE)){
                    User user = serializator.login(username, password, this.ACCOUNTS_FILE);
                    deliveryService.setUser(user);

                    this.loginView.dispose();

                    this.deliveryService.loadInfo();
                    this.employeeView.prepareGui();
                    this.employeeView.setVisible(true);
                    this.employeeView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    ClientView clientView = new ClientView("Client operations",this.deliveryService,this.employeeView);
                    clientView.setVisible(true);
                    clientView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                }
                else this.createOptionPane("Account already exists with this username");
            }
        }
    }

    private void createOptionPane(String message){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,message);
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }
}
