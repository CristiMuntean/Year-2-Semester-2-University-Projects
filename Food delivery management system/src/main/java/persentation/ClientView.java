package persentation;

import businessLayer.DeliveryService;
import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class ClientView extends JFrame {
    private JPanel contentPane;
    private JPanel clientPanel;
    private JLabel titleLabel;
    private JTable productsTable;
    private JButton searchButton;
    private JButton orderButton;
    private JButton logOutButton;

    private ClientController clientController = new ClientController(this);

    public ClientView(String name, DeliveryService deliveryService, EmployeeView employeeView){
        super(name);
        this.clientController.setDeliveryService(deliveryService);
        this.clientController.setEmployeeView(employeeView);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(new Dimension(700,600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,1));
        this.prepareClientPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareClientPanel() {
        this.clientPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx=0.5;
        c.weighty=0.5;

        this.titleLabel = new JLabel("Client Operations");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        c.gridx=0;
        c.gridy=0;
        c.gridwidth = 9;
        this.clientPanel.add(this.titleLabel,c);

        HashSet<MenuItem> items = this.clientController.getDeliveryService().getMenuItems();
        String[] header = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        Object[][] data = new Object[items.size()][7];
        int i=0;
        for(MenuItem menuItem:items){
            data[i][0]= menuItem.getTitle();
            data[i][1]= menuItem.getRating();
            data[i][2]= menuItem.getCalories();
            data[i][3]= menuItem.getProteins();
            data[i][4]= menuItem.getFats();
            data[i][5]= menuItem.getSodium();
            data[i][6]= menuItem.getPrice();
            i++;
        }
        this.productsTable = new JTable(data,header){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(this.productsTable);
        this.productsTable.setFillsViewportHeight(true);
        c.gridy=1;
        c.gridheight=6;
        c.fill=GridBagConstraints.HORIZONTAL;
        this.clientPanel.add(scrollPane,c);

        this.searchButton = new JButton("Search Products");
        this.searchButton.setFont(new Font("Sans-Serif", Font.PLAIN,20));
        this.searchButton.addActionListener(this.clientController);
        this.searchButton.setActionCommand("SEARCH");
        c.gridheight=1;
        c.gridy=7;
        c.gridwidth=3;
        c.gridx=0;
        this.clientPanel.add(this.searchButton,c);

        this.orderButton = new JButton("Order");
        this.orderButton.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.orderButton.addActionListener(this.clientController);
        this.orderButton.setActionCommand("ORDER");
        c.gridx=3;
        this.clientPanel.add(this.orderButton,c);

        this.logOutButton = new JButton("Log Out");
        this.logOutButton.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.logOutButton.addActionListener(this.clientController);
        this.logOutButton.setActionCommand("LOG OUT");
        c.gridx=6;
        this.clientPanel.add(this.logOutButton,c);

        this.contentPane.add(this.clientPanel);
    }

    public ClientController getClientController() {
        return clientController;
    }

    public JTable getProductsTable() {
        return productsTable;
    }
}
