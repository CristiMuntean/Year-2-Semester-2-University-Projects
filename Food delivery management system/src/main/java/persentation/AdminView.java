package persentation;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class AdminView extends JFrame {
    private JPanel contentPane;
    private JPanel adminPanel;
    private JLabel titleLabel;
    private JTable productsTable;
    private JButton importButton;
    private JButton manageProductsButton;
    private JButton generateReportsButton;
    private JButton logOutButton;

    private AdminController controller = new AdminController(this);

    public AdminView(String name, DeliveryService deliveryService){
        super(name);
        this.controller.setDeliveryService(deliveryService);
        this.prepareGui();
    }

    public void clearPanel(){
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
        this.contentPane.setLayout(new GridBagLayout());
    }

    public void prepareGui() {
        this.setSize(new Dimension(700,600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,1));
        this.prepareAdminPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareAdminPanel() {
        this.adminPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.weighty = 0.5;

        this.titleLabel = new JLabel("Admin Operations");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 8;
        this.adminPanel.add(this.titleLabel,c);

        HashSet<businessLayer.MenuItem> items = this.controller.getDeliveryService().getMenuItems();
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
        this.adminPanel.add(scrollPane,c);

        this.importButton = new JButton("Import products");
        this.importButton.setFont(new Font("Sans-Serif", Font.PLAIN,20));
        this.importButton.addActionListener(this.controller);
        this.importButton.setActionCommand("IMPORT");
        c.gridheight=1;
        c.gridy=7;
        c.gridwidth=2;
        c.gridx=0;
        this.adminPanel.add(this.importButton,c);

        this.manageProductsButton = new JButton("Manage Products");
        this.manageProductsButton.setFont(new Font("Sans-Serif", Font.PLAIN,20));
        this.manageProductsButton.addActionListener(this.controller);
        this.manageProductsButton.setActionCommand("MANAGE");
        c.gridx = 2;
        this.adminPanel.add(this.manageProductsButton,c);

        this.generateReportsButton = new JButton("Generate Reports");
        this.generateReportsButton.setFont(new Font("Sans-Serif", Font.PLAIN,20));
        this.generateReportsButton.addActionListener(this.controller);
        this.generateReportsButton.setActionCommand("GENERATE");
        c.gridx=4;
        this.adminPanel.add(this.generateReportsButton,c);

        this.logOutButton = new JButton("Log Out");
        this.logOutButton.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.logOutButton.addActionListener(this.controller);
        this.logOutButton.setActionCommand("LOG OUT");
        c.gridx=6;
        this.adminPanel.add(this.logOutButton,c);

        this.contentPane.add(this.adminPanel);
    }

    public JTable getProductsTable() {
        return productsTable;
    }
}
