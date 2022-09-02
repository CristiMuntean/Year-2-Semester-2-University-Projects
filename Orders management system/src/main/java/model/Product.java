package model;

public class Product {
    private int productId;
    private String productName;
    private double productPrice;
    private int productStock;

    public Product(){
    }

    public Product(int productId, String productName, double productPrice, int productQuantity){
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productQuantity;
    }

    @Override
    public String toString() {
        return "ProductId:" + this.productId + ", ProductName:" + this.productName +
                ", ProductPrice:" + this.productPrice + ", ProductQuantity:" + this.productStock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }


}
