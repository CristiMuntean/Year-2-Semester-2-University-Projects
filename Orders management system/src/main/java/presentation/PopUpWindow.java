package presentation;

import businessLogic.TableFactory;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopUpWindow extends JFrame {
    private JPanel contentPane;
    private JPanel fieldsPanel;
    private JLabel titleLabel;
    private List<JLabel> labelList;
    private List<JTextField> textFields;
    private JButton submitButton;

    private JTextField findTextField;
    private JLabel resultLabel;

    private JTable clientsTable;
    private JTable productsTable;
    private Controller controller;

    /**
     * <p>
     *      Creates a PopUpWindow that contains all the fields of an object an Texfields for inputs
     * </p>
     * @param name title of the window
     * @param fields fields that will need to receive an input
     * @param controller controller responsible for connecting the actions the user takes on the ui with the rest of the app
     */
    public PopUpWindow(String name, List<String> fields, Controller controller){
        super(name);
        this.controller = controller;
        this.prepareGui(name,fields);
    }

    /**
     * <p>
     *      Creates a PopUpWindow that contains two tables of clients and products and two normal input text fields
     * </p>
     * @param name name of the window
     * @param clients clients that will be added in the client table
     * @param products products that will be added in the products table
     * @param controller controller responsible for connecting the actions the user takes on the ui with the rest of the app
     */
    public PopUpWindow(String name, List<Client> clients, List<Product> products, Controller controller){
       super(name);
       this.controller = controller;
       this.prepareAddOrderGui(name,clients,products);
    }

    /**
     * <p>
     *      Creates a PopUpWindow that contains a text field and is used to find a row in a table
     * </p>
     * @param name name of the window
     * @param controller controller responsible for connecting the actions the user takes on the ui with the rest of the app
     */
    public PopUpWindow(String name, Controller controller){
        super(name);
        this.controller = controller;
        this.prepareFindGui(name);
    }

    /**
     * <p>
     *     Prepares the user interface responsible for adding an order
     * </p>
     * @param name name of the window
     * @param clients clients that will be added in the client table
     * @param products products that will be added in the products table
     */
    private void prepareAddOrderGui(String name, List<Client> clients, List<Product> products) {
        this.setSize(new Dimension(500,400));
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareAddOrders(name, clients,products);
        this.setContentPane(this.contentPane);
    }

    /**
     * <p>
     *     Prepares the user interface responsible for finding a row in the table
     * </p>
     * @param name name of the window
     */
    private void prepareFindGui(String name) {
        this.setSize(new Dimension(400,300));
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareFieldsPanelForFind(name);
        this.setContentPane(this.contentPane);
    }
    /**
     * <p>
     *     Clears the whole window
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
     *     Prepares the user interface responsible for displaying the result of the operation
     * </p>
     * @param result the result of the find operation
     */
    public void prepareResultGui(String result){
        this.setSize(new Dimension(400,300));
        this.resultLabel = new JLabel(result);
        this.contentPane.add(this.resultLabel);
        this.setContentPane(this.contentPane);
    }

    /**
     * <p>
     *     Prepares the order panel that contains all the components necessary for the user to input a new order
     * </p>
     * @param name name of the window
     * @param clients clients that will be added in the client table
     * @param products products that will be added in the products table
     */
    private void prepareAddOrders(String name, List<Client> clients, List<Product> products) {
        this.labelList = new ArrayList<>();
        this.textFields = new ArrayList<>();
        this.fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.titleLabel = new JLabel("Add a new order");
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 4;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        this.fieldsPanel.add(this.titleLabel,c);
        this.labelList.add(new JLabel("OrderId:"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth=1;
        c.gridy=1;
        this.fieldsPanel.add(this.labelList.get(0),c);
        this.textFields.add(new JTextField());
        c.gridwidth=3;
        c.gridx=1;
        this.fieldsPanel.add(this.textFields.get(0),c);

        this.labelList.add(new JLabel("ProductQuantity:"));
        c.gridwidth=1;
        c.gridy=2;
        c.gridx=0;
        c.fill = GridBagConstraints.HORIZONTAL;

        this.fieldsPanel.add(this.labelList.get(1),c);
        this.textFields.add(new JTextField());
        c.gridwidth=3;
        c.gridx=1;
        this.fieldsPanel.add(this.textFields.get(1),c);


        TableFactory<Client> clientTableFactory = new TableFactory<>();
        this.clientsTable = clientTableFactory.createTable(clients);
        JScrollPane clientsScrollPane = new JScrollPane(this.clientsTable);
        c.weighty=0.6;
        c.gridwidth=2;
        c.gridheight=3;
        c.gridx=0;
        c.gridy=3;
        c.fill=GridBagConstraints.BOTH;
        this.fieldsPanel.add(clientsScrollPane,c);

        TableFactory<Product> productTableFactory = new TableFactory<>();
        this.productsTable = productTableFactory.createTable(products);
        JScrollPane productsScrollPane = new JScrollPane(this.productsTable);

        c.gridx=2;
        this.fieldsPanel.add(productsScrollPane,c);

        this.submitButton = new JButton("Submit");
        this.submitButton.setActionCommand(name);
        this.submitButton.addActionListener(this.controller);
        c.gridy = 6;
        c.gridwidth=1;
        c.gridheight=1;
        c.weighty=0.1;
        c.gridx=2;
        this.fieldsPanel.add(this.submitButton,c);

        c.gridx=0;
        c.gridy=0;
        this.contentPane.add(this.fieldsPanel,c);
    }

    /**
     * <p>
     *     Prepares the panel that contains all the components necessary for the user to find a row in a table
     *     from the database
     * </p>
     * @param name name of the window
     */
    private void prepareFieldsPanelForFind(String name) {
        this.fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.titleLabel = new JLabel(name);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx=0.1;
        c.gridx=0;
        c.gridy=0;
        this.fieldsPanel.add(this.titleLabel,c);
        this.findTextField = new JTextField();
        c.gridx=1;
        this.fieldsPanel.add(this.findTextField,c);
        this.submitButton = new JButton("Submit");
        this.submitButton.setActionCommand(name);
        this.submitButton.addActionListener(this.controller);
        c.gridy=1;
        this.fieldsPanel.add(this.submitButton,c);
        this.contentPane.add(this.fieldsPanel);
    }

    /**
     * <p>
     *     Prepares the user interface for the user to input a new client or product
     * </p>
     * @param name name of the window
     * @param fields the fields of the client or product
     */
    private void prepareGui(String name, List<String> fields) {
        this.setSize(new Dimension(300,200));
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareFieldsPanel(name,fields);
        this.setContentPane(this.contentPane);
    }
    /**
     * <p>
     *     Prepares the panel that contains all the components necessary for the user to input a new order
     * </p>
     * @param name name of the window
     * @param fields the fields that are going to be added to the panel
     */
    private void prepareFieldsPanel(String name, List<String> fields) {
        this.fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.titleLabel = new JLabel(name);
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        c.weightx=0.1;
        c.gridx=0;
        c.gridy=0;
        this.fieldsPanel.add(this.titleLabel,c);
        int i=0;
        this.labelList = new ArrayList<>();
        this.textFields = new ArrayList<>();
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridwidth=1;
        for(String field: fields){
            c.gridx=0;
            c.gridy=i+1;
            this.labelList.add(new JLabel(field));
            this.fieldsPanel.add(this.labelList.get(i),c);
            this.textFields.add(new JTextField());
            c.gridx=1;
            this.fieldsPanel.add(this.textFields.get(i),c);
            i++;
        }
        this.submitButton = new JButton("Submit");
        this.submitButton.setActionCommand(name);
        this.submitButton.addActionListener(this.controller);
        c.gridy=i+1;
        c.gridx=1;
        this.fieldsPanel.add(this.submitButton,c);
        this.contentPane.add(this.fieldsPanel);
    }

    public List<JTextField> getTextFields() {
        return textFields;
    }
    public JTextField getFindTextField() {
        return findTextField;
    }

    public JTable getClientsTable() {
        return clientsTable;
    }

    public JTable getProductsTable() {
        return productsTable;
    }
}
