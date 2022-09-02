package model;

public class ProductOrder {
    private int orderId;
    private int clientId;
    private int productId;
    private int productQuantity;

    public ProductOrder(){
    }

    public ProductOrder(int orderId, int clientId, int productId, int productQuantity){
        this.orderId = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "OrderId:" + this.orderId + ", ClientId:" + this.clientId + ", ProductId:" + this.productId +
                ", ProductQuantity:" + this.productQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
