package model;

public class Client {
    private int clientId;
    private String clientName;
    private String clientSurname;
    private String address;

    public Client(){

    }

    public Client(int clientId, String clientName, String clientSurname, String address){
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Id:" + this.clientId + ", Name:" + this.clientName + ", Surname:" + this.clientSurname +
                ", address:" + this.address;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public String getAddress() {
        return address;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
