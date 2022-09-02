package model;

public class JoinedOrder {
    private int orderId;
    private String clientName;
    private String clientSurname;
    private String productName;
    private int productQuantity;
    private double orderPrice;

    public JoinedOrder(){
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Order id:" + this.orderId + ", Client name:" + this.clientName + ", Client surname:" + this.clientSurname +
                ", Product name:" + this.productName + ", Product quantity:" + this.productQuantity + ", Order Price:" +
                this.orderPrice;
    }
}
