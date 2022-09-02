package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class View extends JFrame {
    private JPanel contentPane;
    private JPanel polynomialsPanel;
    private JLabel firstPolynomialLabel;
    private JTextField firstPolynomialTextField;
    private JLabel secondPolynomialLabel;
    private JTextField secondPolynomialTextField;
    private ArrayList<JButton> operationsButtons;
    private JLabel resultLabel;
    private JTextField resultTextField;

    Controller controller = new Controller(this);

    public View(String name){
        super(name);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(new Dimension(500,400));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,2));
        this.contentPane.setBorder(new EmptyBorder(10,10,10,10));
        this.preparePolynomialsPanel();
        this.prepareResultPanel();
        this.setContentPane(this.contentPane);
    }

    private void preparePolynomialsPanel() {
        this.polynomialsPanel = new JPanel();
        this.polynomialsPanel.setLayout(new GridLayout(7,2));
        this.firstPolynomialLabel = new JLabel("First Polynomial", JLabel.CENTER);
        this.polynomialsPanel.add(this.firstPolynomialLabel);
        this.firstPolynomialTextField = new JTextField();
        this.polynomialsPanel.add(this.firstPolynomialTextField);
        this.secondPolynomialLabel = new JLabel("Second Polynomial", JLabel.CENTER);
        this.polynomialsPanel.add(this.secondPolynomialLabel);
        this.secondPolynomialTextField = new JTextField();
        this.polynomialsPanel.add(this.secondPolynomialTextField);
        this.addOperations();
    }

    private void addOperations() {
        this.operationsButtons = new ArrayList<>();
        this.operationsButtons.add(new JButton("Add"));
        this.operationsButtons.get(0).setActionCommand("ADD");
        this.operationsButtons.get(0).addActionListener(this.controller);

        this.operationsButtons.add(new JButton("Subtract"));
        this.operationsButtons.get(1).setActionCommand("SUBTRACT");
        this.operationsButtons.get(1).addActionListener(this.controller);

        this.operationsButtons.add(new JButton("Divide"));
        this.operationsButtons.get(2).setActionCommand("DIVIDE");
        this.operationsButtons.get(2).addActionListener(this.controller);

        this.operationsButtons.add(new JButton("Modulo"));
        this.operationsButtons.get(3).setActionCommand("MODULO");
        this.operationsButtons.get(3).addActionListener(this.controller);

        this.operationsButtons.add(new JButton("Multiply"));
        this.operationsButtons.get(4).setActionCommand("MULTIPLY");
        this.operationsButtons.get(4).addActionListener(this.controller);

        this.operationsButtons.add(new JButton("Derive"));
        this.operationsButtons.get(5).setActionCommand("DERIVE");
        this.operationsButtons.get(5).addActionListener(this.controller);

        this.operationsButtons.add(new JButton("Integrate"));
        this.operationsButtons.get(6).setActionCommand("INTEGRATE");
        this.operationsButtons.get(6).addActionListener(this.controller);

        this.operationsButtons.add(new JButton("Exit"));
        this.operationsButtons.get(7).setActionCommand("EXIT");
        this.operationsButtons.get(7).addActionListener(this.controller);

        this.polynomialsPanel.add(this.operationsButtons.get(0));
        this.polynomialsPanel.add(this.operationsButtons.get(1));
        this.polynomialsPanel.add(this.operationsButtons.get(2));
        this.polynomialsPanel.add(this.operationsButtons.get(3));
        this.polynomialsPanel.add(this.operationsButtons.get(4));
        this.polynomialsPanel.add(this.operationsButtons.get(5));
        this.polynomialsPanel.add(this.operationsButtons.get(6));
        this.polynomialsPanel.add(this.operationsButtons.get(7));
    }

    private void prepareResultPanel() {
        this.resultLabel = new JLabel("Result", JLabel.CENTER);
        this.polynomialsPanel.add(this.resultLabel);
        this.resultTextField = new JTextField();
        this.polynomialsPanel.add(this.resultTextField);
        this.contentPane.add(this.polynomialsPanel);
    }

    public JTextField getFirstPolynomialTextField() {
        return firstPolynomialTextField;
    }

    public JTextField getSecondPolynomialTextField() {
        return secondPolynomialTextField;
    }

    public JTextField getResultTextField() {
        return resultTextField;
    }
}
