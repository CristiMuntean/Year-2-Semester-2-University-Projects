package persentation;

import businessLayer.BaseProduct;
import businessLayer.MenuItem;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.jar.JarEntry;

public class SearchView extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JLabel keywordLabel;
    private JTextField keywordTextField;
    private JLabel ratingLabel;
    private JTextField ratingTextField;
    private JLabel caloriesLabel;
    private JTextField caloriesTextField;
    private JLabel proteinsLabel;
    private JTextField proteinsTextField;
    private JLabel fatsLabel;
    private JTextField fatsTextField;
    private JLabel sodiumLabel;
    private JTextField sodiumTextField;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JButton submitButton;


    private JTable resultTable;

    private ClientController controller;

    public SearchView(String name, ClientController controller){
        super(name);
        this.controller = controller;
        this.prepareGui();
    }

    public void prepareGui() {
        this.setSize(new Dimension(700,600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareSearchGui();
        this.setContentPane(this.contentPane);
    }

    private void prepareSearchGui() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridwidth = 4;
        c.gridx=0;
        c.gridy=0;
        this.titleLabel = new JLabel("Input your search filters");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN, 30));
        this.contentPane.add(this.titleLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        this.keywordLabel = new JLabel("Enter keyword:");
        this.keywordLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=1;
        c.gridwidth = 1;
        this.contentPane.add(this.keywordLabel,c);

        this.keywordTextField = new JTextField();
        this.keywordTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.keywordTextField,c);

        this.ratingLabel = new JLabel("Enter rating");
        this.ratingLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=2;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.ratingLabel,c);

        this.ratingTextField = new JTextField();
        this.ratingTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.ratingTextField,c);

        this.caloriesLabel = new JLabel("Enter calories");
        this.caloriesLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=3;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.caloriesLabel,c);

        this.caloriesTextField = new JTextField();
        this.caloriesTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.caloriesTextField,c);

        this.proteinsLabel = new JLabel("Enter proteins");
        this.proteinsLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=4;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.proteinsLabel,c);

        this.proteinsTextField = new JTextField();
        this.proteinsTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.proteinsTextField,c);

        this.fatsLabel = new JLabel("Enter fats");
        this.fatsLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=5;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.fatsLabel,c);

        this.fatsTextField = new JTextField();
        this.fatsTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.fatsTextField,c);

        this.sodiumLabel = new JLabel("Enter sodium");
        this.sodiumLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=6;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.sodiumLabel,c);

        this.sodiumTextField = new JTextField();
        this.sodiumTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.sodiumTextField,c);

        this.priceLabel = new JLabel("Enter price");
        this.priceLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=7;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.priceLabel,c);

        this.priceTextField = new JTextField();
        this.priceTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.priceTextField,c);

        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(this.controller);
        this.submitButton.setActionCommand("Execute Search");
        c.gridwidth=1;
        c.gridy=8;
        c.gridx=3;
        this.contentPane.add(this.submitButton,c);
    }

    public void clearPanel(){
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
        this.contentPane.setLayout(new GridBagLayout());
    }

    public void prepareResultGui(ArrayList<MenuItem> products){
        GridBagConstraints c = new GridBagConstraints();
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=0;
        c.weightx = 0.5;
        c.gridwidth=6;
        c.gridheight=6;
        String[] header = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        Object[][] data = new Object[products.size()][7];
        int i=0;
        for(MenuItem menuItem:products){
            data[i][0]= menuItem.getTitle();
            data[i][1]= menuItem.getRating();
            data[i][2]= menuItem.getCalories();
            data[i][3]= menuItem.getProteins();
            data[i][4]= menuItem.getFats();
            data[i][5]= menuItem.getSodium();
            data[i][6]= menuItem.getPrice();
            i++;
        }
        this.resultTable = new JTable(data,header){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(this.resultTable);
        this.resultTable.setFillsViewportHeight(true);
        this.contentPane.add(scrollPane,c);
    }

    public JTextField getKeywordTextField() {
        return this.keywordTextField;
    }

    public JTextField getRatingTextField() {
        return ratingTextField;
    }

    public JTextField getCaloriesTextField() {
        return caloriesTextField;
    }

    public JTextField getProteinsTextField() {
        return proteinsTextField;
    }

    public JTextField getFatsTextField() {
        return fatsTextField;
    }

    public JTextField getSodiumTextField() {
        return sodiumTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }
}
