package presentation;

import businessLogic.TableFactory;
import model.Client;
import model.JoinedOrder;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class View extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JPanel menuPanel;
    private JLabel clientMenuLabel;
    private JButton clientMenuButton;
    private JLabel productMenuLabel;
    private JButton productMenuButton;
    private JLabel orderMenuLabel;
    private JButton orderMenuButton;

    private JPanel clientPanel;
    private JLabel clientPanelTitleLabel;
    private JTable clientsTable;
    private JButton addClientButton;
    private JButton removeClientButton;
    private JButton editClientButton;
    private JButton findClientByIdButton;
    private JButton printBillButton;

    private JPanel productPanel;
    private JLabel productPanelTitleLabel;
    private JTable productsTable;
    private JButton addProductButton;
    private JButton removeProductButton;
    private JButton editProductButton;
    private JButton findProductByIdButton;

    private JPanel orderPanel;
    private JLabel orderPanelTitleLabel;
    private JTable ordersTable;
    private JButton addOrderButton;
    private JButton removeOrderButton;
    private JButton editOrderButton;
    private JButton findOrderByIdButton;

    private JButton backToMenuButton;
    Controller controller = new Controller(this);

    public View(String name){
        super(name);
        this.prepareGui();
    }

    /**
     * <p>
     *     Prepares the menu user interface
     * </p>
     */
    private void prepareGui() {
        this.setSize(new Dimension(700, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,1));
        this.prepareMenuPanel();
        this.setContentPane(this.contentPane);
    }

    /**
     * <p>
     *     Prepares panel that will contain the components for the menu user interface
     * </p>
     */
    public void prepareMenuPanel() {
        this.menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.ipady=40;
        c.ipadx=40;
        this.titleLabel = new JLabel("Order Management System");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        c.gridwidth = 3;
        c.gridx=0;
        c.gridy=0;
        this.menuPanel.add(this.titleLabel,c);

        this.clientMenuLabel = new JLabel("Go to client operations:");
        this.clientMenuLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.fill = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        c.gridy=1;
        this.menuPanel.add(this.clientMenuLabel,c);
        this.clientMenuButton = new JButton("Enter");
        this.clientMenuButton.setActionCommand("CLIENT");
        this.clientMenuButton.addActionListener(this.controller);
        c.fill = GridBagConstraints.LINE_END;
        c.gridwidth =1;
        c.gridx=2;
        this.menuPanel.add(this.clientMenuButton,c);

        this.productMenuLabel = new JLabel("Go to product operations:");
        this.productMenuLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.fill = GridBagConstraints.LINE_START;
        c.gridwidth=2;
        c.gridx=0;
        c.gridy=2;
        this.menuPanel.add(this.productMenuLabel,c);
        this.productMenuButton = new JButton("Enter");
        this.productMenuButton.setActionCommand("PRODUCT");
        this.productMenuButton.addActionListener(this.controller);
        c.fill = GridBagConstraints.LINE_END;
        c.gridwidth =1;
        c.gridx=2;
        this.menuPanel.add(this.productMenuButton,c);

        this.orderMenuLabel = new JLabel("Go to order operations:");
        this.orderMenuLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.fill = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        this.menuPanel.add(this.orderMenuLabel,c);
        this.orderMenuButton = new JButton("Enter");
        this.orderMenuButton.setActionCommand("ORDER");
        this.orderMenuButton.addActionListener(this.controller);
        c.fill = GridBagConstraints.LINE_END;
        c.gridwidth = 1;
        c.gridx=2;
        this.menuPanel.add(this.orderMenuButton,c);
        this.contentPane.add(this.menuPanel);
    }

    /**
     * <p>
     *     Removes all the components from the window
     * </p>
     */
    public void clearPanel(){
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
        this.contentPane.setLayout(new GridBagLayout());
    }

    /**
     * <p>
     *     Prepares the user interface responsible for making operations with the clients
     * </p>
     * @param objects the list of clients to be added to the table
     */
    public void prepareClientGui(List<Client> objects){
        this.setTitle("Clients");
        this.prepareClientPanel(objects);
        this.setContentPane(this.contentPane);
    }

    /**
     * <p>
     *     Prepares the user interface responsible for making operations with the products
     * </p>
     * @param objects the list of products to be added to the table
     */
    public void prepareProductGui(List<Product> objects){
        this.setTitle("Products");
        this.prepareProductPanel(objects);
        this.setContentPane(this.contentPane);
    }

    /**
     * <p>
     *     Prepares the user interface responsible for making operations with the orders
     * </p>
     * @param objects the list of orders to be added to the table
     */
    public void prepareOrderGui(List<JoinedOrder> objects){
        this.setTitle("Orders");
        this.prepareOrderPanel(objects);
        this.setContentPane(this.contentPane);
    }

    /**
     * <p>
     *     Prepares the panel that contains the components for clients user interface
     * </p>
     * @param objects the list of clients to be added to the table
     */
    private void prepareClientPanel(List<Client> objects){
        this.clientPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx=0.1;
        c.weighty=0.1;
        this.clientPanelTitleLabel = new JLabel("Client Operations");
        this.clientPanelTitleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        this.clientPanel.add(this.clientPanelTitleLabel,c);

        TableFactory<Client> tableFactory = new TableFactory<>();
        this.clientsTable = tableFactory.createTable(objects);
        JScrollPane scrollPane = new JScrollPane(this.clientsTable);
        this.clientsTable.setFillsViewportHeight(true);
        c.gridwidth=3;
        c.gridheight=5;
        c.gridx=0;
        c.gridy=1;
        this.clientPanel.add(scrollPane,c);

        this.addClientButton = new JButton("Add client");
        this.addClientButton.setActionCommand("ADD CLIENT");
        this.addClientButton.addActionListener(this.controller);
        c.ipady=20;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 1;
        this.clientPanel.add(this.addClientButton,c);

        this.editClientButton = new JButton("Edit client");
        this.editClientButton.setActionCommand("EDIT CLIENT");
        this.editClientButton.addActionListener(this.controller);
        c.gridy = 2;
        this.clientPanel.add(this.editClientButton,c);

        this.findClientByIdButton = new JButton("Find client By Id");
        this.findClientByIdButton.setActionCommand("FIND CLIENT");
        this.findClientByIdButton.addActionListener(this.controller);
        c.gridy = 3;
        this.clientPanel.add(this.findClientByIdButton,c);

        this.removeClientButton = new JButton("Remove client");
        this.removeClientButton.setActionCommand("REMOVE CLIENT");
        this.removeClientButton.addActionListener(this.controller);
        c.gridy = 4;
        this.clientPanel.add(this.removeClientButton,c);



        this.backToMenuButton = new JButton("Back to Menu");
        this.backToMenuButton.setActionCommand("BACK TO MENU");
        this.backToMenuButton.addActionListener(this.controller);
        c.gridy = 5;
        this.clientPanel.add(this.backToMenuButton,c);
        this.contentPane.add(this.clientPanel);
    }


    /**
     * <p>
     *     Prepares the panel that contains the components for products user interface
     * </p>
     * @param objects the list of products to be added to the table
     */
    private void prepareProductPanel(List<Product> objects){
        this.productPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx=0.1;
        c.weighty=0.1;
        this.productPanelTitleLabel = new JLabel("Product Operations");
        this.productPanelTitleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        this.productPanel.add(this.productPanelTitleLabel,c);

        TableFactory<Product> tableFactory = new TableFactory<>();
        this.productsTable = tableFactory.createTable(objects);
        JScrollPane scrollPane = new JScrollPane(this.productsTable);
        this.productsTable.setFillsViewportHeight(true);
        c.gridwidth=3;
        c.gridheight=5;
        c.gridx=0;
        c.gridy=1;
        this.productPanel.add(scrollPane,c);

        this.addProductButton = new JButton("Add product");
        this.addProductButton.setActionCommand("ADD PRODUCT");
        this.addProductButton.addActionListener(this.controller);
        c.ipady=20;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 1;
        this.productPanel.add(this.addProductButton,c);

        this.editProductButton = new JButton("Edit product");
        this.editProductButton.setActionCommand("EDIT PRODUCT");
        this.editProductButton.addActionListener(this.controller);
        c.gridy = 2;
        this.productPanel.add(this.editProductButton,c);

        this.findProductByIdButton = new JButton("Find product by id");
        this.findProductByIdButton.setActionCommand("FIND PRODUCT");
        this.findProductByIdButton.addActionListener(this.controller);
        c.gridy = 3;
        this.productPanel.add(this.findProductByIdButton,c);

        this.removeProductButton = new JButton("Remove product");
        this.removeProductButton.setActionCommand("REMOVE PRODUCT");
        this.removeProductButton.addActionListener(this.controller);
        c.gridy = 4;
        this.productPanel.add(this.removeProductButton,c);

        this.backToMenuButton = new JButton("Back to Menu");
        this.backToMenuButton.setActionCommand("BACK TO MENU");
        this.backToMenuButton.addActionListener(this.controller);
        c.gridy = 5;
        this.productPanel.add(this.backToMenuButton,c);

        this.contentPane.add(this.productPanel);
    }
    /**
     * <p>
     *     Prepares the panel that contains the components for orders user interface
     * </p>
     * @param objects the list of orders to be added to the table
     */
    private void prepareOrderPanel(List<JoinedOrder> objects){
        this.orderPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx=0.1;
        c.weighty=0.1;
        this.orderPanelTitleLabel = new JLabel("Order Operations");
        this.orderPanelTitleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        this.orderPanel.add(this.orderPanelTitleLabel,c);

        TableFactory<JoinedOrder> tableFactory = new TableFactory<>();
        this.ordersTable = tableFactory.createTable(objects);
        JScrollPane scrollPane = new JScrollPane(this.ordersTable);
        this.ordersTable.setFillsViewportHeight(true);
        c.gridwidth=3;
        c.gridheight=6;
        c.gridx=0;
        c.gridy=1;
        this.orderPanel.add(scrollPane,c);

        this.addOrderButton = new JButton("Add order");
        this.addOrderButton.setActionCommand("ADD ORDER");
        this.addOrderButton.addActionListener(this.controller);
        c.ipady=20;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 1;
        this.orderPanel.add(this.addOrderButton,c);

        this.editOrderButton = new JButton("Edit order");
        this.editOrderButton.setActionCommand("EDIT ORDER");
        this.editOrderButton.addActionListener(this.controller);
        c.gridy = 2;
        this.orderPanel.add(this.editOrderButton,c);

        this.findOrderByIdButton = new JButton("Find order by id");
        this.findOrderByIdButton.setActionCommand("FIND ORDER");
        this.findOrderByIdButton.addActionListener(this.controller);
        c.gridy = 3;
        this.orderPanel.add(this.findOrderByIdButton,c);

        this.removeOrderButton = new JButton("Remove order");
        this.removeOrderButton.setActionCommand("REMOVE ORDER");
        this.removeOrderButton.addActionListener(this.controller);
        c.gridy = 4;
        this.orderPanel.add(this.removeOrderButton,c);


        this.printBillButton = new JButton("Print Bill");
        this.printBillButton.setActionCommand("PRINT BILL");
        this.printBillButton.addActionListener(this.controller);
        c.gridy=5;
        this.orderPanel.add(this.printBillButton,c);

        this.backToMenuButton = new JButton("Back to Menu");
        this.backToMenuButton.setActionCommand("BACK TO MENU");
        this.backToMenuButton.addActionListener(this.controller);
        c.gridy = 6;
        this.orderPanel.add(this.backToMenuButton,c);


        this.contentPane.add(this.orderPanel);
    }



    public JTable getClientsTable() {
        return clientsTable;
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JTable getOrdersTable() {
        return ordersTable;
    }
}
