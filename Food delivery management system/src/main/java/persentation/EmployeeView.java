package persentation;

import businessLayer.DeliveryService;
import businessLayer.MenuItem;
import businessLayer.Order;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class EmployeeView extends JFrame implements Observer{
    private JPanel contentPane;
    private JPanel employeePanel;
    private JLabel titleLabel;
    private JTable ordersTable;
    private JButton logOutButton;
    private DeliveryService deliveryService;

    private EmployeeController employeeController = new EmployeeController(this);


    public EmployeeView(String name, DeliveryService deliveryService){
        super(name);
        this.deliveryService = deliveryService;
    }

    public void prepareGui() {
        this.setSize(new Dimension(1500,800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,1));
        this.prepareEmployeePanel();
        this.setContentPane(this.contentPane);
    }

    public void prepareActionGui(){
        this.setSize(new Dimension(1500,800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,1));
        this.prepareEmployeePanelWithButton();
        this.setContentPane(this.contentPane);
    }

    private void prepareEmployeePanelWithButton() {
        this.employeePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weighty=0.5;
        c.weightx=0.5;

        c.gridwidth=4;
        c.gridx=0;
        c.gridy=0;
        this.titleLabel = new JLabel("Employee window");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        this.employeePanel.add(this.titleLabel,c);

        String[] header = {"Order Id", "Client Id", "Order date", "Items", "Total Price"};
        HashMap<Order, ArrayList<MenuItem>> orders = this.deliveryService.getOrders();
        Object[][] data = new Object[orders.size()][5];
        int i=0;
        for(Map.Entry<Order,ArrayList<MenuItem>> entry: orders.entrySet()){
            data[i][0] = entry.getKey().getOrderId();
            data[i][1] = entry.getKey().getClientId();
            data[i][2] = entry.getKey().getOrderDate();
            StringBuilder items = new StringBuilder();
            String prefix = "";
            for(MenuItem item:entry.getValue()){
                items.append(prefix).append(item.getTitle());
                prefix = ", ";
            }
            data[i][3] = items.toString();
            data[i][4] = this.deliveryService.calculateOrderPrice(entry.getValue());
            i++;
        }
        this.ordersTable = new JTable(data,header){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.ordersTable.setFillsViewportHeight(true);
        this.ordersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.ordersTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.ordersTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.ordersTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        this.ordersTable.getColumnModel().getColumn(3).setPreferredWidth(1180);
        this.ordersTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(this.ordersTable);
        c.gridy = 1;
        c.gridheight = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.employeePanel.add(scrollPane,c);

        this.logOutButton = new JButton("Log out");
        this.logOutButton.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.logOutButton.addActionListener(this.employeeController);
        this.logOutButton.setActionCommand("LOG OUT");
        c.gridy=7;
        c.gridheight=1;
        this.employeePanel.add(this.logOutButton,c);

        this.contentPane.add(this.employeePanel);
    }

    private void prepareEmployeePanel() {
        this.employeePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weighty=0.5;
        c.weightx=0.5;

        c.gridwidth=4;
        c.gridx=0;
        c.gridy=0;
        this.titleLabel = new JLabel("Employee window");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        this.employeePanel.add(this.titleLabel,c);

        String[] header = {"Order Id", "Client Id", "Order date", "Items", "Total Price"};
        HashMap<Order, ArrayList<MenuItem>> orders = this.deliveryService.getOrders();
        Object[][] data = new Object[orders.size()][5];
        int i=0;
        for(Map.Entry<Order,ArrayList<MenuItem>> entry: orders.entrySet()){
            data[i][0] = entry.getKey().getOrderId();
            data[i][1] = entry.getKey().getClientId();
            data[i][2] = entry.getKey().getOrderDate();
            StringBuilder items = new StringBuilder();
            String prefix = "";
            for(MenuItem item:entry.getValue()){
                items.append(prefix).append(item.getTitle());
                prefix = ", ";
            }
            data[i][3] = items.toString();
            data[i][4] = this.deliveryService.calculateOrderPrice(entry.getValue());
            i++;
        }
        this.ordersTable = new JTable(data,header){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.ordersTable.setFillsViewportHeight(true);
        this.ordersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.ordersTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.ordersTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.ordersTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        this.ordersTable.getColumnModel().getColumn(3).setPreferredWidth(1180);
        this.ordersTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(this.ordersTable);
        c.gridy = 1;
        c.gridheight = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.employeePanel.add(scrollPane,c);

        this.contentPane.add(this.employeePanel);
    }

    private void clearPanel(){
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
        this.contentPane.setLayout(new GridLayout(1,1));
    }

    private void updateTable(){
        this.clearPanel();
        this.prepareGui();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("New order");
        this.updateTable();
    }
}
