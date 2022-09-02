package businessLogic;

import dataAccess.JoinedOrderDAO;
import dataAccess.ProductDAO;
import dataAccess.ProductOrderDAO;
import model.Client;
import model.JoinedOrder;
import model.Product;
import model.ProductOrder;
import presentation.Controller;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OrderOperations implements Operations<ProductOrder> {
    /**
     * <p>
     *     Concrete method for inserting a new Order in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return 1 if operation was successful , 0 if there is an input error, -1 if there is not enough stock
     */
    @Override
    public int insert(Controller controller){
        List<String> inputs = new ArrayList<>();
        for(JTextField field : controller.getPopUpWindow().getTextFields())
            inputs.add(field.getText());

        Client client = new Client();
        Product product = new Product();
        ProductOrder productOrder;
        try {
            for (String input : inputs) {
                if (input.isEmpty()) throw new InputException();
                int testInt = Integer.parseInt(input);
            }
            int colNr = controller.getPopUpWindow().getClientsTable().getColumnCount();
            for (int i = 0; i < colNr; i++) {
                Field field = client.getClass().getDeclaredField(controller.getPopUpWindow().getClientsTable().getColumnName(i));
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), Client.class);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(client, controller.getPopUpWindow().getClientsTable().getValueAt(controller.getPopUpWindow().getClientsTable().getSelectedRow(), i));
            }
            System.out.println(client);
            colNr = controller.getPopUpWindow().getProductsTable().getColumnCount();
            for (int i = 0; i < colNr; i++) {
                Field field = product.getClass().getDeclaredField(controller.getPopUpWindow().getProductsTable().getColumnName(i));
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), Product.class);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(product, controller.getPopUpWindow().getProductsTable().getValueAt(controller.getPopUpWindow().getProductsTable().getSelectedRow(), i));
            }
            System.out.println(product);
            productOrder = new ProductOrder(Integer.parseInt(inputs.get(0)), client.getClientId(), product.getProductId(),
                    Integer.parseInt(inputs.get(1)));
            if (product.getProductStock() - productOrder.getProductQuantity() >= 0) {
                System.out.println(productOrder);
                ProductOrderDAO productOrderDAO = new ProductOrderDAO();
                productOrderDAO.insert(productOrder);
                ProductDAO productDAO = new ProductDAO();
                List<String> filters = new ArrayList<>();
                filters.add("productStock:" + (product.getProductStock() - productOrder.getProductQuantity()));
                productDAO.update(product, filters);
                JoinedOrderDAO joinedOrderDAO = new JoinedOrderDAO();
                return 1;
            }
            else return -1;
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException | NoSuchFieldException ex){
            ex.printStackTrace();
        }catch (NumberFormatException ex){
//            System.out.println("Invalid input for order addition");
            controller.createOptionPane("Invalid input for order addition");
            return 0;
        }catch (InputException ex){
//            System.out.println("One of the input fields are empty");
            controller.createOptionPane("One of the input fields are empty");
            return 0;
        }catch (ArrayIndexOutOfBoundsException ex){
            controller.createOptionPane("One of the tables don't have a row selected");
            return 0;
//            System.out.println("One of the tables don't have a row selected");
        }
        return 1;
    }

    /**
     * <p>
     *     Concrete method for editing an Order in a table of the database
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
        Field[] orderFields = ProductOrder.class.getDeclaredFields();
        int editedQuantity = -1,editedProductId = -1, intCheck;
        try {
            for (int i = 0; i < inputs.size(); i++) {
                if (!inputs.get(i).isEmpty()) {
                    String fieldName = orderFields[i].getName();
                    filters.add(fieldName + ":" + inputs.get(i));
                    intCheck = Integer.parseInt(inputs.get(i));
                    if(i==3)editedQuantity = Integer.parseInt(inputs.get(i));
                    if(i==2)editedProductId = Integer.parseInt(inputs.get(i));
                }
            }
            System.out.println(filters);
            System.out.println(controller.getEditedOrder());
            ProductDAO productDAO = new ProductDAO();
            if(editedProductId != -1 && editedQuantity == -1){
                Product fromProduct = productDAO.findById("productId",controller.getEditedOrder().getProductId());
                Product toProduct = productDAO.findById("productId", editedProductId);

                if(toProduct.getProductStock() >= controller.getEditedOrder().getProductQuantity()){
                    List<String> productFilters = new ArrayList<>();
                    productFilters.add("productStock:" + (fromProduct.getProductStock() + controller.getEditedOrder().getProductQuantity()));
                    productDAO.update(fromProduct,productFilters);

                    productFilters.clear();
                    productFilters.add("productStock:" + (toProduct.getProductStock() - controller.getEditedOrder().getProductQuantity()));
                    productDAO.update(toProduct,productFilters);
                }
                else return false;
            }
            else if(editedProductId != -1 && editedQuantity != -1){
                Product fromProduct = productDAO.findById("productId",controller.getEditedProduct().getProductId());
                Product toProduct = productDAO.findById("productId", editedProductId);

                if(toProduct.getProductStock() >= editedQuantity){
                    List<String> productFilters = new ArrayList<>();
                    productFilters.add("productStock:" + (fromProduct.getProductStock() + controller.getEditedOrder().getProductQuantity()));
                    productDAO.update(fromProduct,productFilters);

                    productFilters.clear();
                    productFilters.add("productStock:" + (toProduct.getProductStock() - editedQuantity));
                    productDAO.update(toProduct,productFilters);
                }
                else return false;
            }
            else if(editedProductId == -1 && editedQuantity != -1){
                Product fromProduct = productDAO.findById("productId",controller.getEditedOrder().getProductId());

                if(fromProduct.getProductStock() + controller.getEditedOrder().getProductQuantity() >= editedQuantity){
                    List<String> productFilters = new ArrayList<>();
                    productFilters.add("productStock:" + (fromProduct.getProductStock() + controller.getEditedOrder().getProductQuantity()));
                    productDAO.update(fromProduct,productFilters);
                    fromProduct = productDAO.findById("productId",controller.getEditedOrder().getProductId());
                    productFilters.clear();
                    productFilters.add("productStock:" + (fromProduct.getProductStock() - editedQuantity));
                    productDAO.update(fromProduct,productFilters);
                }
                else return false;
            }

            ProductOrderDAO productOrderDAO = new ProductOrderDAO();
            if(!filters.isEmpty()){
                productOrderDAO.update(controller.getEditedOrder(),filters);
                return true;
            }
            return false;
        } catch (NumberFormatException ex){
//            System.out.println("Invalid input");
            controller.createOptionPane("Invalid input");
        }
        return false;
    }

    /**
     * <p>
     *     Concrete method for finding an order in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return a ProductOrder object representing the row that was found and null if no row was found
     */
    @Override
    public ProductOrder find(Controller controller) {
        try {
            int id = Integer.parseInt(controller.getPopUpWindow().getFindTextField().getText());
            ProductOrderDAO productOrderDAO = new ProductOrderDAO();
            ProductOrder productOrder = productOrderDAO.findById("orderId",id);
            return productOrder;
        }catch (NumberFormatException ex){
//            System.out.println("Invalid id input");
            controller.createOptionPane("Invalid id input");
        }
        return null;
    }

    /**
     * <p>
     *     Concrete method for removing an order from a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return true if operation was successful and false if not
     */
    @Override
    public boolean remove(Controller controller) {
        ProductOrder productOrder = new ProductOrder();
        try{
            Field field = null;
            field = productOrder.getClass().getDeclaredField(controller.getView().getOrdersTable().getColumnName(0));
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),ProductOrder.class);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(productOrder,controller.getView().getOrdersTable().getValueAt(controller.getView().getOrdersTable().getSelectedRow(),0));
            ProductOrderDAO productOrderDAO = new ProductOrderDAO();
            productOrder = productOrderDAO.findById("orderId", productOrder.getOrderId());
            System.out.println(productOrder);

            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.findById("productId",productOrder.getProductId());
            List<String> filters = new ArrayList<>();
            filters.add("productStock:" + (product.getProductStock() + productOrder.getProductQuantity()));
            productDAO.update(product,filters);
            productOrderDAO.delete(productOrder);
            return true;
        }catch (NoSuchFieldException | IntrospectionException | IllegalAccessException | InvocationTargetException ex){
            ex.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException ex){
//            System.out.println("There is no row selected");
            controller.createOptionPane("There is not row selected");
        }
        return false;
    }

    /**
     * <p>
     *     Creates a .txt file for each order that is in the orders table at the time of invoking the method
     * </p>
     * @param controller the controller object that has access to the gui
     */
    public void printBills(Controller controller){
        int colNr = controller.getView().getOrdersTable().getColumnCount();
        int rowNr = controller.getView().getOrdersTable().getRowCount();
        try{
            for(int row=0;row<rowNr-1;row++){
                File bill = new File("bill"+(row+1)+".txt");
                if (!bill.exists())
                    bill.createNewFile();
                FileWriter fileWriter = new FileWriter(bill);
                JoinedOrder joinedOrder = new JoinedOrder();

                for(int col=0;col<colNr;col++){
                    Field field = JoinedOrder.class.getDeclaredField(controller.getView().getOrdersTable().getColumnName(col));
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),JoinedOrder.class);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(joinedOrder,controller.getView().getOrdersTable().getValueAt(row, col));
                }
                fileWriter.write("Bill for order number " + (row+1)+"\n");
                fileWriter.write(joinedOrder.toString());
                fileWriter.close();
                System.out.println(joinedOrder);
            }
        }catch (NoSuchFieldException | IntrospectionException | InvocationTargetException | IllegalAccessException | IOException ex){
            ex.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("There is no row selected");
        }
    }

    /**
     * <p>
     *     Finds and returns all the orders from the database
     * </p>
     * @return a list of the orders from the database
     */
    public List<JoinedOrder> findAllOrders(){
        JoinedOrderDAO joinedOrderDAO = new JoinedOrderDAO();
        return joinedOrderDAO.findAllWithJoin();
    }
}
