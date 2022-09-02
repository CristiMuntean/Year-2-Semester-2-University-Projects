package gui;

import logic.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class Controller implements ActionListener {
    private SimulationFrame inputFrame;

    public Controller(SimulationFrame frame) {
        this.inputFrame = frame;
    }

    public void addInputFrame(SimulationFrame frame){
        this.inputFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        switch (command){
            case "SUBMIT"->{
                final int clientsNumber, serversNumber, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime,
                        simulationTime;
                String selectedStrategy;
                try{
                    clientsNumber = (Integer)(this.inputFrame.getClientNumberSpinner().getValue());
                    serversNumber = (Integer)(this.inputFrame.getServerNumberSpinner().getValue());
                    minArrivalTime = (Integer)(this.inputFrame.getMinArrivalTimeSpinner().getValue());
                    maxArrivalTime = (Integer)(this.inputFrame.getMaxArrivalTimeSpinner().getValue());
                    minServiceTime = (Integer)(this.inputFrame.getMinServiceTimeSpinner().getValue());
                    maxServiceTime = (Integer)(this.inputFrame.getMaxServiceTimeSpinner().getValue());
                    simulationTime = (Integer)(this.inputFrame.getSimulationTimeSpinner().getValue());
                    selectedStrategy = (String) this.inputFrame.getSimulationComboBox().getSelectedItem();
                    SwingWorker<Void,String> worker = new SwingWorker<>() {

                        @Override
                        protected Void doInBackground() throws Exception {
                            SimulationManager manager = new SimulationManager(clientsNumber,serversNumber,minArrivalTime,
                                    maxArrivalTime,minServiceTime,maxServiceTime,simulationTime,selectedStrategy);
                            Thread t = new Thread(manager);
                            t.start();
                            while(manager.isRunning()){
                                publish(manager.getMessage());
                            }
                            return null;
                        }

                        @Override
                        protected void process(List<String> chunks) {
                            inputFrame.getOutputLabel().setText(chunks.get(chunks.size()-1));
                        }
                    };
                    this.inputFrame.getContentPane().removeAll();
                    this.inputFrame.getContentPane().validate();
                    this.inputFrame.getContentPane().repaint();
                    if(!(minArrivalTime > maxArrivalTime || minServiceTime > maxServiceTime) && clientsNumber>=0 &&
                            serversNumber >= 0 && minArrivalTime >= 0 && maxArrivalTime >= 0 && minServiceTime >=0 &&
                            maxServiceTime >= 0 && simulationTime >= 0){
                        this.inputFrame.prepareSimulationGui();
                        worker.execute();
                    }
                    else {
                        this.inputFrame.PrepareWrongInputGui();
                    }
                }catch (NumberFormatException ex){
                    System.out.println("Bad input");
                    ex.printStackTrace();
                }
            }
            case "EXIT"-> System.exit(0);
            }
        }
    }

