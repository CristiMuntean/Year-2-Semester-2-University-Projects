package businessLayer;

import java.io.Serializable;

public class Order implements Serializable {
    private int orderId;
    private int clientId;
    private String orderDate;

    public Order(int orderId, int clientId, String orderDate){
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderDate = orderDate;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + this.orderId;
        result = result * 31 + this.clientId;
        result = result * 31 + this.orderDate.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(!(obj instanceof Order))return false;
        Order order = (Order) obj;
        return this.clientId == order.clientId && this.orderId == order.orderId && this.orderDate == order.orderDate;
    }

    @Override
    public String toString() {
        return "OrderId:" + this.orderId + ", ClientId:" + this.clientId + ", Order Date:" + this.orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
