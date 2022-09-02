package presentation;

import businessLogic.ClientOperations;
import businessLogic.OrderOperations;
import businessLogic.ProductOperations;
import dataAccess.*;
import model.Client;
import model.JoinedOrder;
import model.Product;
import model.ProductOrder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener{
    private View view;
    private PopUpWindow popUpWindow;
    private Client editedClient;
    private Product editedProduct;
    private ProductOrder editedOrder;

    public Controller(View view){
        this.view = view;
    }

    /**
     * <p>
     *     Processes the actions the user has taken in the user interface and communicates with the logic part of the application
     * </p>
     * @param e the action event that a user has performed when clicking a button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
//        System.out.println(command);
        switch (command){
            case "CLIENT"->{
                this.view.clearPanel();
                ClientDAO clientDAO = new ClientDAO();
                List<Client> clients =  clientDAO.findAll();
                this.view.prepareClientGui(clients);
            }
            case "PRODUCT"->{
                this.view.clearPanel();
                ProductDAO productDAO = new ProductDAO();
                List<Product> products = productDAO.findAll();
                this.view.prepareProductGui(products);
            }
            case "ORDER"->{
                this.view.clearPanel();
                JoinedOrderDAO joinedOrderDAO = new JoinedOrderDAO();
                List<JoinedOrder> orders = joinedOrderDAO.findAllWithJoin();
                this.view.prepareOrderGui(orders);
            }
            case "BACK TO MENU"->{
                this.view.clearPanel();
                this.view.prepareMenuPanel();
            }
            case "ADD CLIENT"->{
                List<String> fields = new ArrayList<>();
                for(int i=0;i<this.view.getClientsTable().getColumnCount();i++)
                    fields.add(this.view.getClientsTable().getColumnName(i));
                this.popUpWindow = new PopUpWindow("Add new client",fields,this);
                this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.popUpWindow.setVisible(true);
            }
            case "Add new client"->{
                ClientOperations clientOperations = new ClientOperations();
                int res = clientOperations.insert(this);
                if(res == 1){
                    this.view.clearPanel();
                    this.view.prepareClientGui(clientOperations.findAllClients());
                    this.popUpWindow.dispose();
                }
                else{
                    this.createOptionPane("Invalid input");
                }
            }
            case "EDIT CLIENT"->{
                int colNr = this.view.getClientsTable().getColumnCount();
                editedClient = new Client();
                try{
                    for (int i=0;i<colNr;i++){
                        Field field = null;
                        field = editedClient.getClass().getDeclaredField(this.view.getClientsTable().getColumnName(i));
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),Client.class);
                        Method method = propertyDescriptor.getWriteMethod();
                        method.invoke(editedClient,this.view.getClientsTable().getValueAt(this.view.getClientsTable().getSelectedRow(),i));
                    }
//                    System.out.println(editedClient);
                    List<String> fields = new ArrayList<>();
                    for(int i=0;i<this.view.getClientsTable().getColumnCount();i++)
                        fields.add(this.view.getClientsTable().getColumnName(i));
                    this.popUpWindow = new PopUpWindow("Edit the selected client",fields,this);
                    this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    this.popUpWindow.setVisible(true);

                }catch (NoSuchFieldException | IntrospectionException | InvocationTargetException | IllegalAccessException ex){
                    ex.printStackTrace();
                }catch (ArrayIndexOutOfBoundsException ex){
//                    System.out.println("There is no row selected");
                    this.createOptionPane("There is no row selected");
                }

            }
            case "Edit the selected client"->{
                ClientOperations clientOperations = new ClientOperations();
                clientOperations.edit(this);
                this.view.clearPanel();
                this.view.prepareClientGui(clientOperations.findAllClients());
                this.popUpWindow.dispose();
            }
            case "FIND CLIENT"->{
                this.popUpWindow = new PopUpWindow("Find a client by id",this);
                this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.popUpWindow.setVisible(true);
            }
            case "Find a client by id"->{
                ClientOperations clientOperations = new ClientOperations();
                Client client = clientOperations.find(this);
                this.popUpWindow.clearPanel();
                if(client!=null)
                    this.popUpWindow.prepareResultGui(client.toString());
                else this.popUpWindow.prepareResultGui("There is no client with that id");
            }
            case "REMOVE CLIENT"->{
                ClientOperations clientOperations = new ClientOperations();
                clientOperations.remove(this);
                this.view.clearPanel();
                this.view.prepareClientGui(clientOperations.findAllClients());
            }
            case "ADD PRODUCT"->{
                List<String> fields = new ArrayList<>();
                for(int i=0;i<this.view.getProductsTable().getColumnCount();i++)
                    fields.add(this.view.getProductsTable().getColumnName(i));
                this.popUpWindow = new PopUpWindow("Add new product",fields,this);
                this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.popUpWindow.setVisible(true);
            }
            case "Add new product"->{
                ProductOperations productOperations = new ProductOperations();
                int res = productOperations.insert(this);
                if(res == 1){
                    this.view.clearPanel();
                    this.view.prepareProductGui(productOperations.findAllProducts());
                    this.popUpWindow.dispose();
                }
                else{
                    this.createOptionPane("Invalid input");
                }
            }
            case "EDIT PRODUCT"->{
                int colNr = this.view.getProductsTable().getColumnCount();
                editedProduct = new Product();
                try{
                    for (int i=0;i<colNr;i++){
                        Field field = null;
                        field = editedProduct.getClass().getDeclaredField(this.view.getProductsTable().getColumnName(i));
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),Product.class);
                        Method method = propertyDescriptor.getWriteMethod();
                        method.invoke(editedProduct,this.view.getProductsTable().getValueAt(this.view.getProductsTable().getSelectedRow(),i));
                    }
//                    System.out.println(editedProduct);
                    List<String> fields = new ArrayList<>();
                    for(int i=0;i<this.view.getProductsTable().getColumnCount();i++)
                        fields.add(this.view.getProductsTable().getColumnName(i));
                    this.popUpWindow = new PopUpWindow("Edit the selected product",fields,this);
                    this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    this.popUpWindow.setVisible(true);

                }catch (NoSuchFieldException | IntrospectionException | InvocationTargetException | IllegalAccessException ex){
                    ex.printStackTrace();
                }catch (ArrayIndexOutOfBoundsException ex){
                    this.createOptionPane("There is no row selected");
                }
            }
            case "Edit the selected product"->{
                ProductOperations productOperations = new ProductOperations();
                productOperations.edit(this);
                this.view.clearPanel();
                this.view.prepareProductGui(productOperations.findAllProducts());
                this.popUpWindow.dispose();
            }
            case "FIND PRODUCT"->{
                this.popUpWindow = new PopUpWindow("Find a product by id",this);
                this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.popUpWindow.setVisible(true);
            }
            case "Find a product by id"->{
                ProductOperations productOperations = new ProductOperations();
                Product product = productOperations.find(this);
                this.popUpWindow.clearPanel();
                if(product!=null)
                    this.popUpWindow.prepareResultGui(product.toString());
                    else this.popUpWindow.prepareResultGui("There is no product with that id");
            }
            case "REMOVE PRODUCT"->{
                ProductOperations productOperations = new ProductOperations();
                productOperations.remove(this);
                this.view.clearPanel();
                this.view.prepareProductGui(productOperations.findAllProducts());
            }
            case "ADD ORDER"->{
                List<String> fields = new ArrayList<>();
                for (Field field: ProductOrder.class.getDeclaredFields())
                    fields.add(field.getName());
                ClientOperations clientOperations = new ClientOperations();
                ProductOperations productOperations = new ProductOperations();
                this.popUpWindow = new PopUpWindow("Add new order",clientOperations.findAllClients(),productOperations.findAllProducts(),this);
                this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.popUpWindow.setVisible(true);
            }
            case "Add new order"->{
                OrderOperations orderOperations = new OrderOperations();
                int res = orderOperations.insert(this);
                if(res==1){
                    this.view.clearPanel();
                    this.view.prepareOrderGui(orderOperations.findAllOrders());
                    this.popUpWindow.dispose();
                }
                else if(res==-1){
                    this.popUpWindow.clearPanel();
                    this.popUpWindow.prepareResultGui("Not enough stock for the selected product");
                    this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    this.popUpWindow.setVisible(true);
//                    System.out.println("NOT ENOUGH STOCK for product");
                    }
            }
            case "EDIT ORDER"->{
                int colNr = this.view.getOrdersTable().getColumnCount();
                editedOrder = new ProductOrder();
                try{
                    Field field = null;
                    field = editedOrder.getClass().getDeclaredField(this.view.getOrdersTable().getColumnName(0));
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),ProductOrder.class);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(editedOrder,this.view.getOrdersTable().getValueAt(this.view.getOrdersTable().getSelectedRow(),0));
                    ProductOrderDAO productOrderDAO = new ProductOrderDAO();
                    editedOrder = productOrderDAO.findById("orderId", editedOrder.getOrderId());
                    List<String> fields = new ArrayList<>();
                    for(Field orderField: ProductOrder.class.getDeclaredFields()){
                        fields.add(orderField.getName());
                    }
                    this.popUpWindow = new PopUpWindow("Edit the selected order",fields,this);
                    this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    this.popUpWindow.setVisible(true);
                }catch (NoSuchFieldException | IntrospectionException | InvocationTargetException | IllegalAccessException ex){
                    ex.printStackTrace();
                }catch (ArrayIndexOutOfBoundsException ex){
//                    System.out.println("There is no row selected");
                    this.createOptionPane("There is no row selected");
                }
            }
            case "Edit the selected order"->{
                OrderOperations orderOperations = new OrderOperations();
                if(orderOperations.edit(this)){
                    this.view.clearPanel();
                    JoinedOrderDAO joinedOrderDAO = new JoinedOrderDAO();
                    this.view.prepareOrderGui(joinedOrderDAO.findAllWithJoin());
                    this.popUpWindow.dispose();
                }
                else{
                    this.popUpWindow.clearPanel();
                    this.popUpWindow.prepareResultGui("Not enough stock for the selected product");
                    this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    this.popUpWindow.setVisible(true);
//                    System.out.println("NOT ENOUGH STOCK for product");
                }
            }
            case "FIND ORDER"->{
                this.popUpWindow = new PopUpWindow("Find an order by id",this);
                this.popUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.popUpWindow.setVisible(true);
            }
            case "Find an order by id"->{
                OrderOperations orderOperations = new OrderOperations();
                ProductOrder productOrder = orderOperations.find(this);
                this.popUpWindow.clearPanel();
                if(productOrder!=null)
                    this.popUpWindow.prepareResultGui(productOrder.toString());
                else this.popUpWindow.prepareResultGui("There is no order with that id");
            }
            case "REMOVE ORDER"-> {
                OrderOperations orderOperations = new OrderOperations();
                orderOperations.remove(this);
                this.view.clearPanel();
                this.view.prepareOrderGui(orderOperations.findAllOrders());
            }
            case "PRINT BILL"->{
                OrderOperations orderOperations = new OrderOperations();
                orderOperations.printBills(this);
            }
        }
    }

    public View getView() {
        return view;
    }

    public PopUpWindow getPopUpWindow() {
        return popUpWindow;
    }

    public Client getEditedClient() {
        return editedClient;
    }

    public Product getEditedProduct() {
        return editedProduct;
    }

    public ProductOrder getEditedOrder() {
        return editedOrder;
    }

    public void createOptionPane(String message){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,message);
    }
}
