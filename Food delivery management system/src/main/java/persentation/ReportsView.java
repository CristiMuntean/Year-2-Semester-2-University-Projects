package persentation;

import javax.swing.*;
import java.awt.*;

public class ReportsView extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JLabel startHourLabel;
    private JTextField startHourTextField;
    private JLabel endHourLabel;
    private JTextField endHourTextField;
    private JLabel minNumberProductsLabel;
    private JTextField minNumberProductsTextField;
    private JLabel minNumberOrdersLabel;
    private JTextField minNumberOrdersTextField;
    private JLabel minOrderValueLabel;
    private JTextField minOrderValueTextField;
    private JLabel dayLabel;
    private JTextField dayTextField;
    private JButton submitButton;

    private JTextField hoursReportTextField;
    private JTextField productsReportTextField;
    private JTextField clientsReportTextField;
    private JTextField dayReportTextField;

    private AdminController controller;

    public ReportsView(String name, AdminController controller){
        super(name);
        this.controller = controller;
        this.prepareGui(name);
    }

    private void prepareGui(String name) {
        this.setSize(new Dimension(700,600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareReportsGui(name);
        this.setContentPane(this.contentPane);
    }

    private void prepareReportsGui(String name) {
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=0.5;
        c.weightx=0.5;
        c.fill =GridBagConstraints.CENTER;
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=4;
        this.titleLabel = new JLabel(name);
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        this.contentPane.add(this.titleLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        this.startHourLabel = new JLabel("Input the start hour:");
        this.startHourLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridy=1;
        c.gridwidth=1;
        this.contentPane.add(this.startHourLabel,c);

        this.startHourTextField = new JTextField();
        this.startHourTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.startHourTextField,c);

        this.endHourLabel = new JLabel("Input the end hour:");
        this.endHourLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridy=2;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.endHourLabel,c);

        this.endHourTextField = new JTextField();
        this.endHourTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.endHourTextField,c);

        this.minNumberProductsLabel = new JLabel("Input the minimum number of products:");
        this.minNumberProductsLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridy=3;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.minNumberProductsLabel,c);

        this.minNumberProductsTextField = new JTextField();
        this.minNumberProductsTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.minNumberProductsTextField,c);

        this.minNumberOrdersLabel = new JLabel("Input the minimum number of orders:");
        this.minNumberOrdersLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridy=4;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.minNumberOrdersLabel,c);

        this.minNumberOrdersTextField = new JTextField();
        this.minNumberOrdersTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.minNumberOrdersTextField,c);

        this.minOrderValueLabel = new JLabel("Input the minimum value of an order:");
        this.minOrderValueLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridy=5;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.minOrderValueLabel,c);

        this.minOrderValueTextField = new JTextField();
        this.minOrderValueTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.minOrderValueTextField,c);

        this.dayLabel =new JLabel("Input the day:");
        this.dayLabel.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridy=6;
        c.gridx=0;
        c.gridwidth=1;
        this.contentPane.add(this.dayLabel,c);

        this.dayTextField = new JTextField();
        this.dayTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        c.gridx=1;
        c.gridwidth=3;
        this.contentPane.add(this.dayTextField,c);

        this.submitButton = new JButton("Submit");
        this.submitButton.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.submitButton.addActionListener(this.controller);
        this.submitButton.setActionCommand("Generate reports");
        c.gridx=3;
        c.gridy=7;
        c.gridwidth=1;
        this.contentPane.add(this.submitButton,c);
    }

    public void clearPanel(){
        this.contentPane.removeAll();
        this.contentPane.revalidate();
        this.contentPane.repaint();
        this.setSize(new Dimension(2000,800));
        this.contentPane.setLayout(new GridBagLayout());
    }

    public void prepareResultsPanel(String hoursReport, String productsReport, String clientsReport, String dayReport){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.1;
        c.weighty = 0.5;
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=4;
        this.titleLabel = new JLabel("Reports generated");
        this.titleLabel.setFont(new Font("Serif",Font.PLAIN,30));
        this.contentPane.add(this.titleLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy=1;
        this.hoursReportTextField = new JTextField();
        this.hoursReportTextField.setText(hoursReport);
        this.hoursReportTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.contentPane.add(this.hoursReportTextField,c);

        c.gridy=2;
        this.productsReportTextField = new JTextField();
        this.productsReportTextField.setText(productsReport);
        this.productsReportTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.contentPane.add(this.productsReportTextField,c);

        c.gridy=3;
        this.clientsReportTextField = new JTextField();
        this.clientsReportTextField.setText(clientsReport);
        this.clientsReportTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.contentPane.add(this.clientsReportTextField,c);

        c.gridy=4;
        this.dayReportTextField = new JTextField();
        this.dayReportTextField.setText(dayReport);
        this.dayReportTextField.setFont(new Font("Sans-Serif",Font.PLAIN,20));
        this.contentPane.add(this.dayReportTextField,c);
    }

    public JTextField getStartHourTextField() {
        return startHourTextField;
    }

    public JTextField getEndHourTextField() {
        return endHourTextField;
    }

    public JTextField getMinNumberProductsTextField() {
        return minNumberProductsTextField;
    }

    public JTextField getMinNumberOrdersTextField() {
        return minNumberOrdersTextField;
    }

    public JTextField getMinOrderValueTextField() {
        return minOrderValueTextField;
    }

    public JTextField getDayTextField() {
        return dayTextField;
    }
}
