package persentation;

import javax.swing.*;
import java.awt.*;

public class TypeView extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JLabel productTypeLabel;
    private JComboBox<String> productTypeComboBox;
    private JButton submitButton;

    private AdminController controller;

    public TypeView(String name, AdminController controller){
        super(name);
        this.controller = controller;
        this.prepareGui(name);
    }

    private void prepareGui(String name) {
        this.setSize(new Dimension(700,300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareTypeGui(name);
        this.setContentPane(this.contentPane);
    }

    private void prepareTypeGui(String name) {

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        this.titleLabel = new JLabel(name);
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN, 30));
        this.contentPane.add(this.titleLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        this.productTypeLabel = new JLabel("Enter product type:");
        this.productTypeLabel.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridy=1;
        c.gridwidth = 1;
        this.contentPane.add(this.productTypeLabel,c);

        this.productTypeComboBox = new JComboBox<>();
        this.productTypeComboBox.addItem("Basic Product");
        this.productTypeComboBox.addItem("Composite Product");
        this.productTypeComboBox.setFont(new Font("Sans-Serif",Font.PLAIN, 20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.productTypeComboBox,c);

        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(this.controller);
        this.submitButton.setActionCommand("Select type");
        c.gridx=3;
        c.gridy=2;
        c.gridwidth=1;
        this.contentPane.add(this.submitButton,c);
    }

    public JComboBox<String> getProductTypeComboBox() {
        return productTypeComboBox;
    }

}
