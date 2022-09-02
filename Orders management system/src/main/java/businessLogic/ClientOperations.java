package businessLogic;

import dataAccess.ClientDAO;
import model.Client;
import presentation.Controller;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClientOperations implements Operations<Client>{
    /**
     * <p>
     *     Concrete method for inserting a new Client in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return 1 if operation was successful and 0 if not
     */
    @Override
    public int insert(Controller controller) {
        List<String> inputs = new ArrayList<>();
        for(JTextField field : controller.getPopUpWindow().getTextFields())
            inputs.add(field.getText());

        Field[] clientFields = Client.class.getDeclaredFields();
        Client client = new Client();
        try {
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i).isEmpty()) throw new InputException();
                String fieldName = clientFields[i].getName();
                PropertyDescriptor propertyDescriptor = null;
                propertyDescriptor = new PropertyDescriptor(fieldName, Client.class);
                Method method = propertyDescriptor.getWriteMethod();
                if (i == 0) {
                    int value = Integer.parseInt(inputs.get(i));
                    method.invoke(client, value);
                } else {
                    method.invoke(client, inputs.get(i));
                }
            }
//            System.out.println(client);
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.insert(client);
            return 1;
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex){
            ex.printStackTrace();
        }catch (NumberFormatException ex){
//            System.out.println("Invalid input for user addition");
        }catch (InputException ex){
//            System.out.println("One of the input fields are empty");
        }
        return 0;
    }

    /**
     * <p>
     *     Concrete method for editing a Client in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return true if operation was successful and false if not
     */
    @Override
    public boolean edit(Controller controller) {
        List<String> inputs = new ArrayList<>();
        List<String> filters = new ArrayList<>();
        for(JTextField field : controller.getPopUpWindow().getTextFields())
            inputs.add(field.getText());

        Field[] clientFields = Client.class.getDeclaredFields();
        try {
            for (int i = 0; i < inputs.size(); i++) {
                if (!inputs.get(i).isEmpty()) {
                    String fieldName = clientFields[i].getName();
                    if(i==0){
                        int val = Integer.parseInt(inputs.get(i));
                    }
                    filters.add(fieldName + ":" + inputs.get(i));
                }
            }
            ClientDAO clientDAO = new ClientDAO();
            if(!filters.isEmpty()){
                clientDAO.update(controller.getEditedClient(),filters);
                return true;
            }
            return false;
        }catch (NumberFormatException ex){
                System.out.println("Invalid id input");
        }
        return false;
    }
    /**
     * <p>
     *     Concrete method for finding a client in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return a Client object representing the row that was found and null if no row was found
     */
    @Override
    public Client find(Controller controller) {
        try {
            int id = Integer.parseInt(controller.getPopUpWindow().getFindTextField().getText());
            ClientDAO clientDAO = new ClientDAO();
            Client client = clientDAO.findById("clientId",id);
            return client;
        }catch (NumberFormatException ex){
            System.out.println("Invalid id input");
        }
        return null;
    }

    /**
     * <p>
     *     Concrete method for removing a client from a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return true if operation was successful and false if not
     */
    @Override
    public boolean remove(Controller controller) {
        int colNr = controller.getView().getClientsTable().getColumnCount();
        Client client = new Client();
        try {
            for (int i = 0; i < colNr; i++) {
                Field field = null;
                field = client.getClass().getDeclaredField(controller.getView().getClientsTable().getColumnName(i));
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), Client.class);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(client, controller.getView().getClientsTable().getValueAt(controller.getView().getClientsTable().getSelectedRow(), i));
            }
            System.out.println(client);
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.delete(client);
            return true;
        } catch (NoSuchFieldException | IntrospectionException | IllegalAccessException | InvocationTargetException ex){
        ex.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException ex){
        System.out.println("There is no row selected");
        }
        return false;
    }

    /**
     * <p>
     *     Finds and returns all the clients from the database
     * </p>
     * @return a list of the clients from the database
     */
    public List<Client> findAllClients(){
        ClientDAO clientDAO = new ClientDAO();
        return clientDAO.findAll();
    }
}
