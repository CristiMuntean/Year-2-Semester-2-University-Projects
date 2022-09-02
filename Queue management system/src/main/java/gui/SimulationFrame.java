package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JPanel inputPanel;
    private JLabel clientNumberLabel;
    private JSpinner clientNumberSpinner;
    private JLabel serverNumberLabel;
    private JSpinner serverNumberSpinner;
    private JLabel arrivalTimeLabel;
    private JSpinner minArrivalTimeSpinner;
    private JSpinner maxArrivalTimeSpinner;
    private JLabel serviceTimeLabel;
    private JSpinner minServiceTimeSpinner;
    private JSpinner maxServiceTimeSpinner;
    private JLabel simulationTimeLabel;
    private JSpinner simulationTimeSpinner;
    private JLabel simulationStrategyLabel;
    private JComboBox<String> simulationComboBox;
    private JButton submitButton;

    private JPanel simulationPanel;
    private JLabel outputLabel;
    private JPanel buttonsPanel;
    private JButton exitButton;

    Controller controller = new Controller(this) ;

    public SimulationFrame(String name){
        super(name);
        this.prepareGui();
        this.controller.addInputFrame(this);
    }

    private void prepareGui() {
        this.setSize(new Dimension(700, 500));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,3));
        this.contentPane.setBorder(new EmptyBorder(10,10,10,10));
        this.prepareInputPanel();
        this.setContentPane(this.contentPane);
    }

    public JComboBox<String> getSimulationComboBox() {
        return simulationComboBox;
    }

    private void prepareInputPanel() {
        this.inputPanel = new JPanel(new GridLayout(8,3));
        this.titleLabel = new JLabel("Input the values for the simulation",JLabel.CENTER);
        this.clientNumberLabel = new JLabel("Give the number of clients:", JLabel.LEFT);
        this.clientNumberSpinner = new JSpinner();
        this.serverNumberLabel = new JLabel("Give the number of servers:", JLabel.LEFT);
        this.serverNumberSpinner = new JSpinner();
        this.arrivalTimeLabel = new JLabel("Give the arrival time(min-max):", JLabel.LEFT);
        this.minArrivalTimeSpinner = new JSpinner();
        this.maxArrivalTimeSpinner = new JSpinner();
        this.serviceTimeLabel = new JLabel("Give the service time(min-max):", JLabel.LEFT);
        this.minServiceTimeSpinner = new JSpinner();
        this.maxServiceTimeSpinner = new JSpinner();
        this.simulationTimeLabel = new JLabel("Give the simulation time:", JLabel.LEFT);
        this.simulationTimeSpinner =  new JSpinner();
        this.simulationStrategyLabel = new JLabel("Give the simulation strategy:",JLabel.LEFT);
        String[] options = {"Shortest time", "Shortest queue"};
        this.simulationComboBox = new JComboBox<>(options);

        this.submitButton = new JButton("Submit");
        this.submitButton.setActionCommand("SUBMIT");
        this.submitButton.addActionListener(this.controller);

        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(this.titleLabel);
        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(clientNumberLabel);
        this.inputPanel.add(clientNumberSpinner);
        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(serverNumberLabel);
        this.inputPanel.add(serverNumberSpinner);
        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(arrivalTimeLabel);
        this.inputPanel.add(minArrivalTimeSpinner);
        this.inputPanel.add(maxArrivalTimeSpinner);
        this.inputPanel.add(serviceTimeLabel);
        this.inputPanel.add(minServiceTimeSpinner);
        this.inputPanel.add(maxServiceTimeSpinner);
        this.inputPanel.add(simulationTimeLabel);
        this.inputPanel.add(simulationTimeSpinner);
        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(simulationStrategyLabel);
        this.inputPanel.add(simulationComboBox);
        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(this.newEmptyLabel());
        this.inputPanel.add(submitButton);
        this.contentPane.add(this.inputPanel);
    }

    public void prepareSimulationGui() {
        this.setTitle("Simulation");
        this.prepareSimulationPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareSimulationPanel() {
        this.simulationPanel = new JPanel(new GridLayout(1,1));
        this.outputLabel = new JLabel("Simulation is starting",JLabel.CENTER);
        this.simulationPanel.add(this.outputLabel);
//        JPanel exitPanel = new JPanel(new GridLayout(1,1));
//        this.exitButton = new JButton("Exit");
//        this.exitButton.setActionCommand("EXIT");
//        this.exitButton.addActionListener(this.controller);
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.newEmptyLabel());
//        exitPanel.add(this.exitButton);
//        this.simulationPanel.add(exitPanel);
        this.contentPane.add(this.simulationPanel);
    }

    public void PrepareWrongInputGui(){
        this.setTitle("Wrong Input");
        this.prepareWrongInputPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareWrongInputPanel() {
        this.simulationPanel = new JPanel(new GridLayout(2,1));
        this.outputLabel = new JLabel("You have entered a wrong input",JLabel.CENTER);

        this.exitButton = new JButton("Exit");
        this.exitButton.setActionCommand("EXIT");
        this.exitButton.addActionListener(this.controller);

        this.buttonsPanel = new JPanel(new GridLayout(3,3));
        this.buttonsPanel.setBorder(new EmptyBorder(40,40,40,40));
        this.simulationPanel.add(this.outputLabel);
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.newEmptyLabel());
        this.buttonsPanel.add(this.exitButton);
        this.simulationPanel.add(buttonsPanel);
        this.contentPane.add(this.simulationPanel);
    }


    private JLabel newEmptyLabel(){
        return new JLabel("", JLabel.CENTER);
    }

    public JSpinner getClientNumberSpinner() {
        return clientNumberSpinner;
    }

    public JSpinner getServerNumberSpinner() {
        return serverNumberSpinner;
    }

    public JSpinner getMinArrivalTimeSpinner() {
        return minArrivalTimeSpinner;
    }

    public JSpinner getMaxArrivalTimeSpinner() {
        return maxArrivalTimeSpinner;
    }

    public JSpinner getMinServiceTimeSpinner() {
        return minServiceTimeSpinner;
    }

    public JSpinner getMaxServiceTimeSpinner() {
        return maxServiceTimeSpinner;
    }

    public JSpinner getSimulationTimeSpinner() {
        return simulationTimeSpinner;
    }

    public JLabel getOutputLabel() {
        return outputLabel;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
