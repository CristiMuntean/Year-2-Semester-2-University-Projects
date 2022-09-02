package businessLayer;

import java.util.ArrayList;

public interface IDeliveryServiceProcessing {
    //for admin

    /**
     * <p>
     * If the user has the "Admin" access level, the menuItem list from the delivery service will be updated with
     * the products from the "products.csv" file
     * </p>
     *
     * @pre true
     * @post true
     */
    public abstract void importProducts();

    /**
     * <p>
     * If the user has the "Admin" access level, the menuItem list present in the delivery service will be modified
     * according to the command given
     * </p>
     *
     * @param command     can bee "Add", "Delete" or "Edit"
     * @param managedItem the item that will be managed in the list, depending on the command
     * @param newItem     the item that will replace managedItem parameter in the list if the command is "Edit"
     * @pre command != null
     * @pre managedItem != null
     * @pre (command == "Add" || command == "Delete" ==> newItem == null)
     * @post (command == "Add" ==> getSize() == getSize()@pre + 1) && (command == "Delete" ==> getSize() == getSize()&pre - 1) &&
     * (command == "Edit" ==> getSize() == getSize()@pre)
     */
    public abstract void manageProducts(String command, MenuItem managedItem, MenuItem newItem);

    /**
     * <p>
     * If the user has the "Admin" access level, reports will be generated according to the parameters
     * </p>
     *
     * @param startHour         the start hour for the hours report
     * @param endHour           the end hour for the hours report
     * @param minNumberProducts the minimum amount a product should have been ordered in order to appear in the products report
     * @param minNumberClients  the minimum number of ordered for a client in order to appear in the clients report
     * @param minOrderValue     the minim value of an order that a client should make in order to appear in the clients report
     *                          - if the client has an order with the value more than the minimum value, he will appear in the report
     * @param day               the day in which the products should have been order in order to appear in the day report along with the number of orders
     * @pre startHour >= 0 && startHour<=24
     * @pre endHour >= 0 && endHour <= 24
     * @pre minNumberProducts >= 0
     * @pre minNumberClients >= 0
     * @pre minOrderValue >= 0
     * @pre day >= 0
     * @post true
     */
    public abstract void generateReports(int startHour, int endHour, int minNumberProducts, int minNumberClients, int minOrderValue, int day);


    //for client

    /**
     * <p>
     * If the user has the "Client" access level, an order will be created that contains the order id, client id, date of
     * the order and the items that have been ordered and the total price of the order. A bill will be created in
     * ".txt" format
     * </p>
     *
     * @param clientId the id of the client that made the order
     * @param items    the items that describe the order
     * @pre clientId >= 0
     * @pre items.getSize() > 0
     */
    public abstract void createOrder(int clientId, ArrayList<MenuItem> items);

    /**
     * <p>
     * If the user has the "Client" access level, the user will be able to search the items that contain some specified
     * filters
     * </p>
     * @param filters the array of the filters, each filter being in the form "FilterName:Value"
     * @pre filters.getSize() >=0
     * @post @result.getSize() >= 0
     *
     */
    public abstract ArrayList<MenuItem> searchProduct(ArrayList<String> filters);
}