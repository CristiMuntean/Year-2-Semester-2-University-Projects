package dataAccess;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import businessLayer.MenuItem;
import businessLayer.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Serializator{
    private DeliveryService deliveryService;
    public Serializator(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }


    private static Function<String, MenuItem> mapToItem = (line) -> {

        String[] p = line.split(",");

        BaseProduct item = new BaseProduct(p[0].substring(0,p[0].length()-1),Double.parseDouble(p[1]),Integer.parseInt(p[2]),Integer.parseInt(p[3]),
                Integer.parseInt(p[4]),Integer.parseInt(p[5]),Integer.parseInt(p[6]));
        return (MenuItem)item;
    };

    public static HashSet<MenuItem> deserializeFile(String fileName){
        HashSet<MenuItem> products = new HashSet<>();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileIn));
            products = (HashSet<MenuItem>) br.lines().skip(1).map(mapToItem).collect(Collectors.toSet());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void generateHourlyReport(HashMap<Order, ArrayList<MenuItem>> orders, String fileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(orders);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Order, ArrayList<MenuItem>> deserializeHoursReport(){
        try {
            FileInputStream fileInputStream = new FileInputStream("Hours report.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            HashMap<Order, ArrayList<MenuItem>> readOrders = (HashMap<Order, ArrayList<MenuItem>>) objectInputStream.readObject();
            return readOrders;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateProductsMoreThanMinReport(HashMap<MenuItem,Integer> products, String fileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(products);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<MenuItem, Integer> deserializeProductsMoreThanMinReport(){
        try {
            FileInputStream fileInputStream = new FileInputStream("Products ordered report.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            HashMap<MenuItem, Integer> readProducts = (HashMap<MenuItem, Integer>) objectInputStream.readObject();
            return readProducts;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateClientsWithFiltersReport(HashMap<Integer, Integer> clients, String fileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(clients);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Integer> deserializeClientsWithFiltersReport(){
        try {
            FileInputStream fileInputStream = new FileInputStream("Clients orders report.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            HashMap<Integer, Integer> readClients = (HashMap<Integer, Integer>) objectInputStream.readObject();

            return readClients;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateProductsInSpecifiedDayReport(HashMap<MenuItem, Integer> products, String fileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(products);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<MenuItem, Integer> deserializeProductsInSpecifiedDayReport(){
        try {
            FileInputStream fileInputStream = new FileInputStream("Products ordered in a specific day report.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            HashMap<MenuItem, Integer> readProducts = (HashMap<MenuItem, Integer>) objectInputStream.readObject();
            return readProducts;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveInfo(HashMap<Order,ArrayList<MenuItem>> orders, HashSet<MenuItem> menuItems, int latestOrderId, String fileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(orders);
            objectOutputStream.writeObject(menuItems);
            objectOutputStream.writeObject(latestOrderId);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadInfo(String fileName){
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = null;

            objectInputStream = new ObjectInputStream(fileInputStream);

            HashMap<Order, ArrayList<MenuItem>> readOrders = (HashMap<Order, ArrayList<MenuItem>>) objectInputStream.readObject();
            HashSet<MenuItem> readItems = (HashSet<MenuItem>) objectInputStream.readObject();
            int readLatestOrder = (int) objectInputStream.readObject();

            this.deliveryService.setOrders(readOrders);
            this.deliveryService.setMenuItems(readItems);
            this.deliveryService.setLatestOrderId(readLatestOrder);

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User login(String username, String password, String fileName){
        try {
            File accountsFile = new File(fileName);
            if(!accountsFile.exists())accountsFile.createNewFile();

            if(accountsFile.length()==0){
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
                objectOutputStream.close();
                fileOutputStream.close();
            }

            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<User> accounts = (ArrayList<User>) objectInputStream.readObject();
            if(accounts!=null){
                for(User account: accounts){
                    if(account.getUsername().equals(username) && account.getPassword().equals(password))return account;
                }
            }
            objectInputStream.close();;
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(String username, String password, String fileName){
        try {
            File accountsFile = new File(fileName);
            if(!accountsFile.exists())accountsFile.createNewFile();
            if(accountsFile.length() == 0){
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
                objectOutputStream.close();
                fileOutputStream.close();
            }
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<User> accounts = (ArrayList<User>) objectInputStream.readObject();
            if(accounts != null){
                for(User account: accounts){
                    if(account.getUsername().equals(username))return false;
                }
                objectInputStream.close();
                fileInputStream.close();
            }
            if(accounts == null){
                accounts = new ArrayList<>();
                accounts.add(new User(username,password,"Client",1));
            }
            else{
                int lastUserId=-1;
                for (User account:accounts)
                    if(account.getClientId()>lastUserId)lastUserId=account.getClientId();
                accounts.add(new User(username,password,"Client",lastUserId));
            }

            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean addAdmin(String username, String password, String fileName){
        try {
            File accountsFile = new File(fileName);
            if(!accountsFile.exists())accountsFile.createNewFile();
            if(accountsFile.length() == 0){
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
                objectOutputStream.close();
                fileOutputStream.close();
            }
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<User> accounts = (ArrayList<User>) objectInputStream.readObject();
            if(accounts != null){
                for(User account: accounts){
                    if(account.getUsername().equals(username) && account.getPassword().equals(password))return false;
                }
                objectInputStream.close();;
                fileInputStream.close();
            }
            if(accounts == null){
                accounts = new ArrayList<>();
                accounts.add(new User(username,password,"Admin",1));
            }
            else{
                int lastUserId=-1;
                for(User account:accounts)
                    if(account.getClientId() > lastUserId)lastUserId = account.getClientId();
                accounts.add(new User(username,password,"Admin",lastUserId));
            }


            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean addEmployee(String username, String password, String fileName){
        try {
            File accountsFile = new File(fileName);
            if(!accountsFile.exists())accountsFile.createNewFile();
            if(accountsFile.length() == 0){
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
                objectOutputStream.close();
                fileOutputStream.close();
            }
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<User> accounts = (ArrayList<User>) objectInputStream.readObject();
            if(accounts != null){
                for(User account: accounts){
                    if(account.getUsername().equals(username) && account.getPassword().equals(password))return false;
                }
                objectInputStream.close();;
                fileInputStream.close();
            }
            if(accounts == null){
                accounts = new ArrayList<>();
                accounts.add(new User(username,password,"Employee",1));
            }
            else{
                int lastUserId=-1;
                for(User account: accounts)
                    if (account.getClientId() > lastUserId) lastUserId = account.getClientId();
                accounts.add(new User(username,password,"Employee",lastUserId));
            }

            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
