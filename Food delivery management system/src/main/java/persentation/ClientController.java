package persentation;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClientController implements ActionListener {
    private ClientView clientView;
    private EmployeeView employeeView;
    private DeliveryService deliveryService;
    private SearchView searchView;

    public ClientController(ClientView clientView){
        this.clientView = clientView;
        this.searchView = new SearchView("Search for a product",this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "LOG OUT"->{
                this.clientView.dispose();
                this.employeeView.dispose();
                LoginView loginView = new LoginView("Login");
                loginView.setVisible(true);
                loginView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
            case "SEARCH"->{
                this.searchView.clearPanel();
                this.searchView.prepareGui();
                searchView.setVisible(true);
                searchView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
            case "ORDER"->{
                ArrayList<MenuItem> selectedItems = new ArrayList<>();
                int[] rows = this.clientView.getProductsTable().getSelectedRows();
                if(rows.length == 0)this.createOptionPane("No row is selected");
                else{
                    for(int i=0;i<rows.length;i++) {
                        BaseProduct product = new BaseProduct(
                                (String) this.clientView.getProductsTable().getValueAt(rows[i],0),
                                (Double) this.clientView.getProductsTable().getValueAt(rows[i],1),
                                (Integer) this.clientView.getProductsTable().getValueAt(rows[i],2),
                                (Integer) this.clientView.getProductsTable().getValueAt(rows[i],3),
                                (Integer) this.clientView.getProductsTable().getValueAt(rows[i],4),
                                (Integer) this.clientView.getProductsTable().getValueAt(rows[i],5),
                                (Integer) this.clientView.getProductsTable().getValueAt(rows[i],6)
                        );
                        selectedItems.add(product);
                    }
                    this.deliveryService.createOrder(this.deliveryService.getUser().getClientId(),selectedItems);
                    this.deliveryService.saveInfo();
                }
            }
            case "Execute Search"->{
                boolean errorsExist = false;
                ArrayList<String> filters = new ArrayList<>();
                String keywordFilter = searchView.getKeywordTextField().getText();
                if(!keywordFilter.isEmpty())filters.add("Keyword:"+keywordFilter);
                try{
                    if(!searchView.getRatingTextField().getText().isEmpty()){
                        Double ratingFilter = Double.parseDouble(searchView.getRatingTextField().getText());
                        filters.add("Rating:"+ratingFilter);
                    }
                }catch (NumberFormatException ex){
                    this.createOptionPane("Incorrect rating input");
                    errorsExist = true;
                }
                try{
                    if(!searchView.getCaloriesTextField().getText().isEmpty()){
                        Integer caloriesFilter = Integer.parseInt(searchView.getCaloriesTextField().getText());
                        filters.add("Calories:"+caloriesFilter);
                    }
                }catch (NumberFormatException ex){
                    this.createOptionPane("Incorrect calories input");
                    errorsExist = true;
                }
                try{
                    if(!searchView.getProteinsTextField().getText().isEmpty()){
                        Integer proteinsFilter = Integer.parseInt(searchView.getProteinsTextField().getText());
                        filters.add("Proteins:"+proteinsFilter);
                    }
                }catch (NumberFormatException ex){
                    this.createOptionPane("Incorrect proteins input");
                    errorsExist = true;
                }
                try{
                    if(!searchView.getFatsTextField().getText().isEmpty()){
                        Integer fatsFilter = Integer.parseInt(searchView.getFatsTextField().getText());
                        filters.add("Fats:"+fatsFilter);
                    }
                }catch (NumberFormatException ex){
                    this.createOptionPane("Incorrect fats input");
                    errorsExist = true;
                }
                try{
                    if(!searchView.getSodiumTextField().getText().isEmpty()){
                        Integer sodiumFilter = Integer.parseInt(searchView.getSodiumTextField().getText());
                        filters.add("sodium:"+sodiumFilter);
                    }
                }catch (NumberFormatException ex){
                    this.createOptionPane("Incorrect sodium input");
                    errorsExist = true;
                }
                try{
                    if(!searchView.getPriceTextField().getText().isEmpty()){
                        Integer priceFilter = Integer.parseInt(searchView.getPriceTextField().getText());
                        filters.add("Price:"+priceFilter);
                    }
                }catch (NumberFormatException ex){
                    this.createOptionPane("Incorrect price input");
                    errorsExist = true;
                }
                if(!errorsExist){
                    ArrayList<MenuItem> products = this.deliveryService.searchProduct(filters);
                    this.searchView.clearPanel();
                    this.searchView.prepareResultGui(products);
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

    public void setEmployeeView(EmployeeView employeeView) {
        this.employeeView = employeeView;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }
}
