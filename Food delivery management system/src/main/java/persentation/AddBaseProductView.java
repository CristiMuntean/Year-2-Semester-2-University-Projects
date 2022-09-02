package persentation;

import javax.swing.*;
import java.awt.*;

public class AddBaseProductView extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JLabel productTitleLabel;
    private JTextField titleTextField;
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


    private AdminController controller;

    public AddBaseProductView(String name, AdminController controller){
        super(name);
        this.controller = controller;
        this.prepareGui(name);
    }

    private void prepareGui(String name) {
        this.setSize(new Dimension(700,600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareSearchGui(name);
        this.setContentPane(this.contentPane);
    }

    private void prepareSearchGui(String name) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridwidth = 4;
        c.gridx=0;
        c.gridy=0;
        this.titleLabel = new JLabel(name);
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN, 30));
        this.contentPane.add(this.titleLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        this.productTitleLabel = new JLabel("Enter title:");
        this.productTitleLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=1;
        c.gridwidth = 1;
        this.contentPane.add(this.productTitleLabel,c);

        this.titleTextField = new JTextField();
        this.titleTextField.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.titleTextField,c);

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
        this.submitButton.setActionCommand("Add item");
        c.gridwidth=1;
        c.gridy=8;
        c.gridx=3;
        this.contentPane.add(this.submitButton,c);
    }

    public JTextField getTitleTextField() {
        return this.titleTextField;
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
