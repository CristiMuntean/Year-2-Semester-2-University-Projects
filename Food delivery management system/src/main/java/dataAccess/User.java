package dataAccess;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String access;
    private int clientId;


    public User(String username, String password, String access, int clientId){
        this.username = username;
        this.password = password;
        this.access = access;
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccess() {
        return access;
    }

    public int getClientId() {
        return clientId;
    }
}
