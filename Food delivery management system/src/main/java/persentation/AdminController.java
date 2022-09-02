package persentation;

import businessLayer.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminController implements ActionListener {
    private AdminView adminView;
    private DeliveryService deliveryService;
    private ManageView manageView;
    private BaseProduct editedProduct;
    private EditView editView = new EditView("Edit the selected product",this);
    private ReportsView reportsView;
    private TypeView typeView;
    private AddCompositeProductView compositeProductView;
    private AddBaseProductView baseProductView;
    public AdminController(AdminView adminView){
        this.adminView = adminView;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "LOG OUT"->{
                this.adminView.dispose();
                LoginView loginView = new LoginView("Login");
                loginView.setVisible(true);
                loginView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
            case "IMPORT"->{
                this.deliveryService.importProducts();
                this.createOptionPane("Items have been imported successfully");

                this.adminView.clearPanel();
                this.adminView.prepareGui();
            }
            case "MANAGE"->{
                this.manageView = new ManageView("Manage products",this);
                manageView.setVisible(true);
                manageView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
            case "GENERATE"->{
                this.reportsView = new ReportsView("Generate reports",this);
                this.reportsView.setVisible(true);
                this.reportsView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
            case "Generate reports"->{
                int startHour=-1, endHour=-1, minNumberProducts=-1, minNumberOrders=-1, minOrderValue=-1, day=-1;
                boolean validInput=true;
                try{
                    startHour = Integer.parseInt(this.reportsView.getStartHourTextField().getText());
                    if(startHour<0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid start hour input");
                    validInput = false;
                }
                try{
                    endHour = Integer.parseInt(this.reportsView.getEndHourTextField().getText());
                    if(endHour<0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid end hour input");
                    validInput = false;
                }
                try{
                    minNumberProducts = Integer.parseInt(this.reportsView.getMinNumberProductsTextField().getText());
                    if(minNumberProducts<0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid minimum number of products input");
                    validInput = false;
                }
                try{
                    minNumberOrders = Integer.parseInt(this.reportsView.getMinNumberOrdersTextField().getText());
                    if(minNumberOrders<0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid minimum number of orders input");
                    validInput = false;
                }
                try{
                    minOrderValue = Integer.parseInt(this.reportsView.getMinOrderValueTextField().getText());
                    if(minOrderValue<0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid minimum value of an order input");
                    validInput = false;
                }
                try{
                    day = Integer.parseInt(this.reportsView.getDayTextField().getText());
                    if(day<0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid day input");
                    validInput = false;
                }
                if(validInput){
                    this.deliveryService.generateReports(startHour,endHour,minNumberProducts,minNumberOrders,minOrderValue,day);
                    HashMap<Order, ArrayList<MenuItem>>hoursReport = this.deliveryService.deserializeHoursReport();
                    HashMap<MenuItem,Integer> productsReport = this.deliveryService.deserializeProductsMoreThanMinReport();
                    HashMap<Integer, Integer> clientsReport = this.deliveryService.deserializeClientsWithFiltersReport();
                    HashMap<MenuItem,Integer> dayReport = this.deliveryService.deserializeProductsInSpecifiedDayReport();
                    StringBuilder hoursReportBuilder = new StringBuilder();
                    StringBuilder productsReportBuilder = new StringBuilder();
                    StringBuilder clientsReportBuilder = new StringBuilder();
                    StringBuilder dayReportBuilder = new StringBuilder();
                    for(Map.Entry<Order,ArrayList<MenuItem>> entry: hoursReport.entrySet()){
                        hoursReportBuilder.append(entry.getKey().toString()).append("\n");
                        for(MenuItem menuItem: entry.getValue()){
                            hoursReportBuilder.append(menuItem.toString()).append("\n");
                        }
                    }

                    for(Map.Entry<MenuItem,Integer> entry: productsReport.entrySet()){
                        productsReportBuilder.append("Item: ").append(entry.getKey())
                                .append("--->Number of ordered times:").append(entry.getValue()).append("\n");
                    }

                    for(Map.Entry<Integer,Integer> entry: clientsReport.entrySet()){
                        clientsReportBuilder.append("ClientId: ").append(entry.getKey()).
                                append("--->Number of ordered times:").append(entry.getValue()).append("\n");
                    }

                    for(Map.Entry<MenuItem,Integer> entry: dayReport.entrySet()){
                        dayReportBuilder.append("Product: ").append(entry.getKey())
                                .append("--->Number of ordered times:").append(entry.getValue()).append("\n");
                    }

                    this.reportsView.clearPanel();
                    this.reportsView.prepareResultsPanel(hoursReportBuilder.toString(), productsReportBuilder.toString(),
                            clientsReportBuilder.toString(), dayReportBuilder.toString());

                }
            }
            case "ADD"->{
                this.typeView = new TypeView("Select the type of the new product",this);
                this.typeView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                this.typeView.setVisible(true);
            }
            case "Select type"->{
                String type = (String) this.typeView.getProductTypeComboBox().getSelectedItem();
                if(type.equals("Composite Product")){
                    this.typeView.dispose();
                    this.compositeProductView = new AddCompositeProductView("Add a new composite product",this);
                    this.compositeProductView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    this.compositeProductView.setVisible(true);
                }
                else{
                    this.typeView.dispose();
                    this.baseProductView = new AddBaseProductView("Add a new base product",this);
                    this.baseProductView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    this.baseProductView.setVisible(true);
                }

            }
            case "Add composite product"->{
                if(!this.compositeProductView.getProductTitleTextField().getText().isEmpty()){
                    String title = this.compositeProductView.getProductTitleTextField().getText();
                    if(this.compositeProductView.getProductsTable().getSelectedRows().length == 0)
                        this.createOptionPane("Select at least a row");
                    else{
                        CompositeProduct addedCompositeProduct = new CompositeProduct(title);
                        int[] rows = this.compositeProductView.getProductsTable().getSelectedRows();
                        for(int i=0;i<rows.length;i++) {
                            BaseProduct product = new BaseProduct(
                                    (String) this.compositeProductView.getProductsTable().getValueAt(rows[i],0),
                                    (Double) this.compositeProductView.getProductsTable().getValueAt(rows[i],1),
                                    (Integer) this.compositeProductView.getProductsTable().getValueAt(rows[i],2),
                                    (Integer) this.compositeProductView.getProductsTable().getValueAt(rows[i],3),
                                    (Integer) this.compositeProductView.getProductsTable().getValueAt(rows[i],4),
                                    (Integer) this.compositeProductView.getProductsTable().getValueAt(rows[i],5),
                                    (Integer) this.compositeProductView.getProductsTable().getValueAt(rows[i],6)
                            );
                            addedCompositeProduct.addItem(product);
                        }
                        this.deliveryService.manageProducts("Add", addedCompositeProduct,null);
                        this.compositeProductView.dispose();

                        this.adminView.clearPanel();
                        this.adminView.prepareGui();

                        this.manageView.clearPanel();
                        this.manageView.prepareGui();
                    }
                }
                else this.createOptionPane("Must input a product title");

            }
            case "Add item"->{
                String title="";
                double rating=-1;
                int calories=-1,proteins=-1,fats=-1,sodium=-1,price=-1;
                boolean isValid = true;
                if(!this.baseProductView.getTitleTextField().getText().isEmpty()){
                    title = this.baseProductView.getTitleTextField().getText();
                }else{
                    this.createOptionPane("Invalid title input");
                    isValid = false;
                }
                try{
                    rating = Double.parseDouble(this.baseProductView.getRatingTextField().getText());
                    if(rating<0 || rating>5)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid rating input");
                    isValid = false;
                }
                try {
                    calories = Integer.parseInt(this.baseProductView.getCaloriesTextField().getText());
                    if(calories<0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid calories input");
                    isValid = false;
                }
                try {
                    proteins = Integer.parseInt(this.baseProductView.getProteinsTextField().getText());
                    if(proteins < 0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid proteins input");
                    isValid = false;
                }
                try {
                    fats = Integer.parseInt(this.baseProductView.getFatsTextField().getText());
                    if(fats < 0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid fats input");
                    isValid = false;
                }
                try {
                    sodium = Integer.parseInt(this.baseProductView.getSodiumTextField().getText());
                    if(sodium < 0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid sodium input");
                    isValid = false;
                }
                try {
                    price = Integer.parseInt(this.baseProductView.getPriceTextField().getText());
                    if(price < 0)throw new NumberFormatException();
                }catch (NumberFormatException ex){
                    this.createOptionPane("Invalid price input");
                    isValid = false;
                }
                if(isValid){
                    BaseProduct addedBaseProduct = new BaseProduct(title, rating, calories, proteins, fats, sodium, price);
                    this.deliveryService.manageProducts("Add", addedBaseProduct,null);
                    this.baseProductView.dispose();

                    this.adminView.clearPanel();
                    this.adminView.prepareGui();

                    this.manageView.clearPanel();
                    this.manageView.prepareGui();
                }

            }
            case "EDIT"->{
                if(this.manageView.getProductsTable().getSelectedRow() == -1){
                    this.createOptionPane("No row selected in the table");
                }
                else if(this.manageView.getProductsTable().getSelectedRows().length > 1){
                    this.createOptionPane("Select only one row");
                }
                else{
                    String title = (String) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),0);
                    double rating = (double) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),1);
                    int calories = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),2);
                    int proteins = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),3);
                    int fats = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),4);
                    int sodium = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),5);
                    int price = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),6);

                    this.editedProduct = new BaseProduct(title,rating,calories,proteins,fats,sodium,price);
                    this.editView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    this.editView.setVisible(true);
                }
            }
            case "Execute Edit"->{
                double rating=-1;
                int calories=-1,proteins=-1,fats=-1,sodium=-1,price=-1;
                boolean canPerform = true;
                String title = this.editView.getTitleTextField().getText();
                if(!this.editView.getRatingTextField().getText().isEmpty()){
                    try{
                        rating = Double.parseDouble(this.editView.getRatingTextField().getText());
                        if(rating<0 || rating>5)throw new NumberFormatException();
                    }
                    catch (NumberFormatException ex){
                        this.createOptionPane("Invalid rating input");
                        canPerform = false;
                    }
                }
                if(!this.editView.getCaloriesTextField().getText().isEmpty()){
                    try{
                        calories = Integer.parseInt(this.editView.getCaloriesTextField().getText());
                        if(calories<0)throw new NumberFormatException();
                    }catch (NumberFormatException ex){
                        this.createOptionPane("Invalid calories input");
                        canPerform = false;
                    }
                }
                if(!this.editView.getProteinsTextField().getText().isEmpty()){
                    try{
                        proteins = Integer.parseInt(this.editView.getProteinsTextField().getText());
                        if(proteins<0)throw new NumberFormatException();
                    }catch (NumberFormatException ex){
                        this.createOptionPane("Invalid proteins input");
                        canPerform = false;
                    }
                }
                if(!this.editView.getFatsTextField().getText().isEmpty()){
                    try {
                        fats = Integer.parseInt(this.editView.getFatsTextField().getText());
                        if(fats<0)throw new NumberFormatException();
                    }catch (NumberFormatException ex){
                        this.createOptionPane("Invalid fats input");
                        canPerform = false;
                    }
                }
                if(!this.editView.getSodiumTextField().getText().isEmpty()){
                    try {
                        sodium = Integer.parseInt(this.editView.getSodiumTextField().getText());
                        if(sodium<0)throw new NumberFormatException();
                    }catch (NumberFormatException ex){
                        this.createOptionPane("Invalid sodium input");
                        canPerform = false;
                    }
                }
                if(!this.editView.getPriceTextField().getText().isEmpty()){
                    try {
                        price = Integer.parseInt(this.editView.getPriceTextField().getText());
                        if(price<0)throw new NumberFormatException();
                    }catch (NumberFormatException ex){
                        this.createOptionPane("Invalid price input");
                        canPerform = false;
                    }
                }
                if(canPerform){
                    BaseProduct newProduct = new BaseProduct(
                            !title.isEmpty() ? title : this.editedProduct.getTitle(),
                            rating >=0 ? rating : this.editedProduct.getRating(),
                            calories >=0 ? calories : this.editedProduct.getCalories(),
                            proteins >=0 ? proteins : this.editedProduct.getProteins(),
                            fats >=0 ? fats : this.editedProduct.getFats(),
                            sodium >=0 ? sodium : this.editedProduct.getSodium(),
                            price >=0 ? price : this.editedProduct.getPrice()
                    );
                    this.deliveryService.manageProducts("Edit",this.editedProduct,newProduct);
                    this.editView.dispose();

                    this.adminView.clearPanel();
                    this.adminView.prepareGui();

                    this.manageView.clearPanel();
                    this.manageView.prepareGui();
                }

            }
            case "REMOVE"->{
                if(this.manageView.getProductsTable().getSelectedRow() == -1){
                    this.createOptionPane("No row selected in the table");
                }
                else if(this.manageView.getProductsTable().getSelectedRows().length > 1){
                    this.createOptionPane("Select only one row");
                }
                else{
                    String title = (String) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),0);
                    double rating = (double) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),1);
                    int calories = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),2);
                    int proteins = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),3);
                    int fats = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),4);
                    int sodium = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),5);
                    int price = (int) this.manageView.getProductsTable().getValueAt(this.manageView.getProductsTable().getSelectedRow(),6);
                    BaseProduct product = new BaseProduct(title,rating,calories,proteins,fats,sodium,price);
                    this.deliveryService.manageProducts("Delete",product,null);

                    this.manageView.clearPanel();
                    this.manageView.prepareGui();

                    this.adminView.clearPanel();
                    this.adminView.prepareGui();
                }
            }
        }
    }

    private void createOptionPane(String message){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,message);
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        this.deliveryService.loadInfo();
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }
}
