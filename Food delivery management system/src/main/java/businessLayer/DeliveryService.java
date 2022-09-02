package businessLayer;

import dataAccess.Serializator;
import dataAccess.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @invariant isWellFormed()
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing{
    private HashMap<Order,ArrayList<MenuItem>> orders;
    private HashSet<MenuItem> menuItems;
    private int latestOrderId;
    private User user;

    public DeliveryService(){
        this.orders = new HashMap<>();
        this.menuItems = new HashSet<>();
        this.latestOrderId = 0;
        this.user = new User("","","",-1);

    }

    protected boolean isWellFormed(){
        if(this.user == null)return false;
        if(!this.user.getAccess().equals("Client") && !this.user.getAccess().equals("Admin") &&
        !this.user.getAccess().equals("Employee"))return false;
        return true;
    }

    @Override
    public void importProducts() {
        if(this.user.getAccess().equals("Admin")){
            this.menuItems = Serializator.deserializeFile("products.csv");
            this.saveInfo();
        }
        assert(this.isWellFormed());
    }

    @Override
    public void manageProducts(String command, MenuItem managedItem, MenuItem newItem) {
        //preconditions
        assert (command != null);
        assert (managedItem != null);
        assert !command.equals("Add") && !command.equals("Delete") || (newItem == null);

        int size = this.menuItems.size();
        if(this.user.getAccess().equals("Admin")){
            switch (command) {
                case "Add"->{
                    this.menuItems.add(managedItem);
                    this.saveInfo();
                }
                case "Delete"->{
                    this.menuItems.remove(managedItem);
                    this.saveInfo();
                }
                case "Edit"->{
                    this.menuItems.remove(managedItem);
                    this.menuItems.add(newItem);
                    this.saveInfo();
                }
            }
        }


        //post conditions
        assert !command.equals("Add") || (this.menuItems.size() == size + 1);
        assert !command.equals("Delete") || (this.menuItems.size() == size - 1);
        assert !command.equals("Edit") || (this.menuItems.size() == size);
        assert (this.isWellFormed());
    }



    private HashMap<Order,ArrayList<MenuItem>> getOrdersInInterval(int startHour, int endHour){
        HashMap<Order,ArrayList<MenuItem>> result = new HashMap<>();
        this.orders.forEach((order, items) -> {
            String date = order.getOrderDate();
            String[] dateParts = date.split(" ");
            String[] hourParts = dateParts[1].split(":");
            if (Integer.parseInt(hourParts[0]) >= startHour && Integer.parseInt(hourParts[0]) <= endHour)
                result.put(order, items);
        });
        return result;
    }

    private void traverseList(HashMap<MenuItem,Integer> numberOfOrders, ArrayList<MenuItem> items){
        items.forEach(item -> {
            if (item instanceof CompositeProduct) {
                traverseList(numberOfOrders, ((CompositeProduct) item).getSubItems());
            }
            else {
                if (!numberOfOrders.containsKey(item)) numberOfOrders.put(item, 1);
                else numberOfOrders.put(item, numberOfOrders.get(item) + 1);
            }
        });
    }

    private HashMap<MenuItem,Integer> getProductsOrderedMoreThanSpecified(int minNumberProducts){
        HashMap<MenuItem,Integer> numberOfOrders = new HashMap<>();
        this.orders.forEach((key, items) -> traverseList(numberOfOrders, items));
        HashMap<MenuItem,Integer> resultItems = new HashMap<>();
        numberOfOrders.forEach((key, value) -> {
            if (value > minNumberProducts) resultItems.put(key, value);
        });
        return resultItems;
    }

    private HashMap<Integer,Integer> getClientsWithFilters(int minNumberOrders, int minOrderValue){
        HashMap<Integer, Integer> clientOrders = new HashMap<>();
        this.orders.forEach((key, value) -> {
            int orderPrice = calculateOrderPrice(value);
            if (orderPrice > minOrderValue) {
                if (!clientOrders.containsKey(key.getClientId())) clientOrders.put(key.getClientId(), 1);
                else clientOrders.put(key.getClientId(), clientOrders.get(key.getClientId()) + 1);
            }
        });
        clientOrders.entrySet().removeIf(entry -> entry.getValue() <= minNumberOrders);
        return clientOrders;
    }

    private HashMap<MenuItem,Integer> getProductsInSpecifiedDay(int day){
        HashMap<MenuItem, Integer> productsNr = new HashMap<>();
        this.orders.forEach((order, value) -> {
            String date = order.getOrderDate();
            String[] fullDateParts = date.split(" ");
            String[] dateParts = fullDateParts[0].split("/");
            if (Integer.parseInt(dateParts[0]) == day) {
                this.traverseList(productsNr, value);
            }
        });
        return productsNr;
    }

    @Override
    public void generateReports(int startHour, int endHour, int minNumberProducts, int minNumberOrders, int minOrderValue, int day) {
        //pre conditions
        assert (startHour >= 0 && startHour<=24);
        assert (endHour >= 0 && endHour <= 24);
        assert (minNumberProducts >= 0);
        assert (minNumberOrders >= 0);
        assert (minOrderValue >= 0);
        assert (day >= 0);
        if(this.user.getAccess().equals("Admin")){
            HashMap<Order, ArrayList<MenuItem>> ordersInInterval = getOrdersInInterval(startHour,endHour);
            HashMap<MenuItem,Integer> productsMoreThanMin = this.getProductsOrderedMoreThanSpecified(minNumberProducts);
            HashMap<Integer, Integer> clientOrders = this.getClientsWithFilters(minNumberOrders,minOrderValue);
            HashMap<MenuItem, Integer> products = this.getProductsInSpecifiedDay(day);

            Serializator serializator = new Serializator(this);
            serializator.generateHourlyReport(ordersInInterval,"Hours report.txt");
            serializator.generateProductsMoreThanMinReport(productsMoreThanMin,"Products ordered report.txt");
            serializator.generateClientsWithFiltersReport(clientOrders,"Clients orders report.txt");
            serializator.generateProductsInSpecifiedDayReport(products,"Products ordered in a specific day report.txt");
        }


        assert (this.isWellFormed());
    }

    public HashMap<Order, ArrayList<MenuItem>> deserializeHoursReport(){
        Serializator serializator = new Serializator(this);
        return serializator.deserializeHoursReport();
    }

    public HashMap<MenuItem, Integer> deserializeProductsMoreThanMinReport(){
        Serializator serializator = new Serializator(this);
        return serializator.deserializeProductsMoreThanMinReport();
    }

    public HashMap<Integer, Integer> deserializeClientsWithFiltersReport(){
        Serializator serializator = new Serializator(this);
        return serializator.deserializeClientsWithFiltersReport();
    }

    public HashMap<MenuItem, Integer> deserializeProductsInSpecifiedDayReport(){
        Serializator serializator = new Serializator(this);
        return serializator.deserializeProductsInSpecifiedDayReport();
    }

    public int calculateOrderPrice(ArrayList<MenuItem> items){
        int sum = 0;
        for(MenuItem item:items){
            if(item instanceof BaseProduct)sum += ((BaseProduct) item).getPrice();
            else if(item instanceof CompositeProduct)sum += calculateOrderPrice(((CompositeProduct) item).getSubItems());
        }
        return sum;
    }

    @Override
    public void createOrder(int clientId, ArrayList<MenuItem> items) {
        //pre conditions
        assert (clientId>=0);
        assert (items.size()>0);

        if(this.user.getAccess().equals("Client")){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            this.latestOrderId++;
            Order order = new Order(this.latestOrderId,clientId,dtf.format(now));
            this.orders.put(order,items);
            File file = new File("Order" + this.latestOrderId + ".txt");
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(order.toString() + "\n");
                for(MenuItem item:items)
                    fileWriter.write(item.toString() + "\n");

                fileWriter.write("\nTotal price of the order:" + String.valueOf(calculateOrderPrice(items)));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setChanged();
            this.notifyObservers();
        }


        assert (this.isWellFormed());
    }

    @Override
    public ArrayList<MenuItem> searchProduct(ArrayList<String> filters) {
        //pre conditions
        assert (filters.size()>=0);


        if(this.user.getAccess().equals("Client")){
            ArrayList<MenuItem> items = new ArrayList<>(this.menuItems);
            for(String filter:filters){
                String[] parts = filter.split(":");
                switch (parts[0]){
                    case "Keyword"->{
                        items = (ArrayList<MenuItem>) items.stream().filter(menuItem ->
                                menuItem.getTitle().contains(parts[1])
                        ).collect(Collectors.toList());
                    }
                    case "Rating"->{
                        items = (ArrayList<MenuItem>) items.stream().filter(menuItem ->
                                menuItem.getRating() == Double.parseDouble(parts[1])
                        ).collect(Collectors.toList());
                    }
                    case "Calories"->{
                        items = (ArrayList<MenuItem>) items.stream().filter(menuItem ->
                                menuItem.getCalories() == Integer.parseInt(parts[1])
                        ).collect(Collectors.toList());
                    }
                    case "Proteins"->{
                        items = (ArrayList<MenuItem>) items.stream().filter(menuItem ->
                                menuItem.getProteins() == Integer.parseInt(parts[1])
                        ).collect(Collectors.toList());
                    }
                    case "Fats"->{
                        items = (ArrayList<MenuItem>) items.stream().filter(menuItem ->
                                menuItem.getFats() == Integer.parseInt(parts[1])
                        ).collect(Collectors.toList());
                    }
                    case "Sodium"->{
                        items = (ArrayList<MenuItem>) items.stream().filter(menuItem ->
                                menuItem.getSodium() == Integer.parseInt(parts[1])
                        ).collect(Collectors.toList());
                    }
                    case "Price"->{
                        items = (ArrayList<MenuItem>) items.stream().filter(menuItem ->
                                menuItem.getPrice() == Integer.parseInt(parts[1])
                        ).collect(Collectors.toList());
                    }
                }
            }


            assert (this.isWellFormed());
            assert (items.size()>=0);
            return items;
        }
        else {
            assert (this.isWellFormed());
            return null;
        }
    }

    public void saveInfo(){
        Serializator serializator = new Serializator(this);
        serializator.saveInfo(this.orders, this.menuItems, this.latestOrderId, "Save log.txt");
    }

    public void loadInfo(){
        Serializator serializator = new Serializator(this);
        serializator.loadInfo("Save log.txt");
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrders() {
        return orders;
    }

    public HashSet<MenuItem> getMenuItems() {
        return menuItems;
    }

    public int getLatestOrderId() {
        return latestOrderId;
    }

    public User getUser() {
        return user;
    }

    public void setOrders(HashMap<Order, ArrayList<MenuItem>> orders) {
        this.orders = orders;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMenuItems(HashSet<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setLatestOrderId(int latestOrderId) {
        this.latestOrderId = latestOrderId;
    }
}
