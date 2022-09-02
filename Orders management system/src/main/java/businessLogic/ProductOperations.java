package businessLogic;

import dataAccess.ProductDAO;
import model.Product;
import presentation.Controller;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProductOperations implements Operations<Product>{
    /**
     * <p>
     *     Concrete method for inserting a new Product in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return 1 if operation was successful and 0 if not
     */
    @Override
    public int insert(Controller controller) {
        List<String> inputs = new ArrayList<>();
        for(JTextField field : controller.getPopUpWindow().getTextFields())
            inputs.add(field.getText());

        Field[] productFields = Product.class.getDeclaredFields();
        Product product = new Product();
        try {
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i).isEmpty()) throw new InputException();
                String fieldName = productFields[i].getName();
                PropertyDescriptor propertyDescriptor = null;
                propertyDescriptor = new PropertyDescriptor(fieldName, Product.class);
                Method method = propertyDescriptor.getWriteMethod();
                if (i == 0 || i == 3) {
                    int value = Integer.parseInt(inputs.get(i));
                    method.invoke(product, value);
                } else if (i == 2) {
                    double value = Double.parseDouble(inputs.get(i));
                    method.invoke(product, value);
                } else {
                    method.invoke(product, inputs.get(i));
                }
            }
            System.out.println(product);
            ProductDAO productDAO = new ProductDAO();
            productDAO.insert(product);
            return 1;
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex){
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Invalid input for product addition");
        }catch (InputException ex){
            System.out.println("One of the input fields are empty");
        }
        return 0;
    }

    /**
     * <p>
     *     Concrete method for editing a Product in a table of the database
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

        Field[] productFields = Product.class.getDeclaredFields();
        int intCheck;
        double doubleCheck;
        try {
            for (int i = 0; i < inputs.size(); i++) {
                if (!inputs.get(i).isEmpty()) {
                    String fieldName = productFields[i].getName();
                    filters.add(fieldName + ":" + inputs.get(i));
                }
                if ((i == 0 || i == 3) && !inputs.get(i).isEmpty()) intCheck = Integer.parseInt(inputs.get(i));
                if (i == 2 && !inputs.get(i).isEmpty()) doubleCheck = Double.parseDouble(inputs.get(i));
            }
            System.out.println(filters);
            ProductDAO productDAO = new ProductDAO();
            if (!filters.isEmpty()){
                productDAO.update(controller.getEditedProduct(), filters);
                return true;
            }
            return false;
        }catch (NumberFormatException ex){
            System.out.println("Invalid input");
        }
        return false;
    }

    /**
     * <p>
     *     Concrete method for finding a product in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return a Product object representing the row that was found and null if no row was found
     */
    @Override
    public Product find(Controller controller) {
        try {
            int id = Integer.parseInt(controller.getPopUpWindow().getFindTextField().getText());
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.findById("productId", id);
            return product;
        }catch (NumberFormatException ex){
            System.out.println("Invalid id input");
        }
        return null;
    }

    /**
     * <p>
     *     Concrete method for removing a product from a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return true if operation was successful and false if not
     */
    @Override
    public boolean remove(Controller controller) {
        int colNr = controller.getView().getProductsTable().getColumnCount();
        Product product = new Product();
        try {
            for (int i = 0; i < colNr; i++) {
                Field field = null;
                field = product.getClass().getDeclaredField(controller.getView().getProductsTable().getColumnName(i));
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), Product.class);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(product, controller.getView().getProductsTable().getValueAt(controller.getView().getProductsTable().getSelectedRow(), i));
            }
            System.out.println(product);
            ProductDAO productDAO = new ProductDAO();
            productDAO.delete(product);
            return true;
        }catch (NoSuchFieldException | IntrospectionException | IllegalAccessException | InvocationTargetException ex){
            ex.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("There is no row selected");
        }
        return false;
    }

    /**
     * <p>
     *     Finds and returns all the products from the database
     * </p>
     * @return a list of the products from the database
     */
    public List<Product> findAllProducts(){
        ProductDAO productDAO = new ProductDAO();
        return productDAO.findAll();
    }
}
