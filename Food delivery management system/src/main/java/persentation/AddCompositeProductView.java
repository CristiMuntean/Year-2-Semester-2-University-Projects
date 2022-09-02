package persentation;

import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class AddCompositeProductView extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JLabel productTitleLabel;
    private JTextField productTitleTextField;
    private JTable productsTable;
    private JButton submitButton;

    private AdminController controller;

    public AddCompositeProductView(String name, AdminController controller){
        super(name);
        this.controller = controller;
        this.prepareGui(name);
    }

    private void prepareGui(String name) {
        this.setSize(new Dimension(700,600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareAddGui(name);
        this.setContentPane(this.contentPane);
    }

    private void prepareAddGui(String name) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth=4;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        this.titleLabel = new JLabel(name);
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        this.contentPane.add(this.titleLabel,c);


        c.fill = GridBagConstraints.HORIZONTAL;
        this.productTitleLabel = new JLabel("Input product title:");
        this.productTitleLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridy=1;
        c.gridwidth=1;
        this.contentPane.add(this.productTitleLabel,c);

        this.productTitleTextField = new JTextField();
        this.productTitleTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridx = 1;
        c.gridwidth = 3;
        this.contentPane.add(this.productTitleTextField,c);


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
        this.productsTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(this.productsTable);
        c.gridy=2;
        c.gridx=0;
        c.gridwidth = 4;
        c.gridheight = 6;
        this.contentPane.add(scrollPane,c);

        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(this.controller);
        this.submitButton.setActionCommand("Add composite product");
        c.gridy=8;
        c.gridx=3;
        c.gridheight=1;
        this.contentPane.add(this.submitButton,c);
    }

    public JTextField getProductTitleTextField() {
        return productTitleTextField;
    }

    public JTable getProductsTable() {
        return productsTable;
    }
}
