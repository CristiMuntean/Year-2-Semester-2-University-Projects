package persentation;

import businessLayer.BaseProduct;
import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class ManageView extends JFrame {
    private JPanel contentPane;
    private JPanel managePanel;
    private JLabel titleLabel;
    private JTable productsTable;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    private AdminController controller;

    public ManageView(String name, AdminController controller){
        super(name);
        this.controller = controller;
        this.prepareGui();
    }

    public void prepareGui() {
        this.setSize(new Dimension(700,600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,1));
        this.prepareManageGui();
        this.setContentPane(this.contentPane);
    }

    private void prepareManageGui() {
        this.managePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridwidth = 6;
        c.gridx=0;
        c.gridy=0;
        this.titleLabel = new JLabel("Manage Products Operations");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN, 30));
        this.managePanel.add(this.titleLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy=1;
        c.gridheight=6;
        HashSet<businessLayer.MenuItem> items = this.controller.getDeliveryService().getMenuItems();
        String[] header = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        Object[][] data = new Object[items.size()][7];
        int i=0;
        for(MenuItem menuItem:items){
            if(menuItem instanceof BaseProduct){
                data[i][0]=((BaseProduct) menuItem).getTitle();
                data[i][1]=((BaseProduct) menuItem).getRating();
                data[i][2]=((BaseProduct) menuItem).getCalories();
                data[i][3]=((BaseProduct) menuItem).getProteins();
                data[i][4]=((BaseProduct) menuItem).getFats();
                data[i][5]=((BaseProduct) menuItem).getSodium();
                data[i][6]=((BaseProduct) menuItem).getPrice();
            }
            i++;
        }
        this.productsTable = new JTable(data,header){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(this.productsTable);
        this.managePanel.add(scrollPane,c);

        this.addButton = new JButton("Add Products");
        this.addButton.setFont(new Font("Sans-Serif", Font.PLAIN,20));
        this.addButton.addActionListener(this.controller);
        this.addButton.setActionCommand("ADD");
        c.gridheight=1;
        c.gridy=7;
        c.gridwidth=2;
        c.gridx=0;
        this.managePanel.add(this.addButton,c);

        this.editButton = new JButton("Edit Product");
        this.editButton.setFont(new Font("Sans-Serif", Font.PLAIN,20));
        this.editButton.addActionListener(this.controller);
        this.editButton.setActionCommand("EDIT");
        c.gridx = 2;
        this.managePanel.add(this.editButton,c);

        this.removeButton = new JButton("Remove Product");
        this.removeButton.setFont(new Font("Sans-Serif", Font.PLAIN,20));
        this.removeButton.addActionListener(this.controller);
        this.removeButton.setActionCommand("REMOVE");
        c.gridx=4;
        this.managePanel.add(this.removeButton,c);


        this.contentPane.add(this.managePanel);
    }

    public void clearPanel(){
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
        this.contentPane.setLayout(new GridBagLayout());
    }

    public JTable getProductsTable() {
        return productsTable;
    }
}
