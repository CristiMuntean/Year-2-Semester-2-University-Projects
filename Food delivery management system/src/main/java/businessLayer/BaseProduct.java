package businessLayer;

import java.io.Serializable;

public class BaseProduct extends MenuItem implements Serializable {

    public BaseProduct(String title, double rating, int calories, int proteins, int fats,
                       int sodium, int price){
        super(title,rating,calories,proteins,fats,sodium,price);
    }

    @Override
    public int computePrice() {
        return this.getPrice();
    }

    @Override
    public String toString() {
        return "Name:" + this.getTitle() + ", Rating:" + this.getRating() + ", Calories:" + this.getCalories() + ", Proteins:" +
                this.getProteins() + ", Fats:" + this.getFats() + ", Sodium:" + this.getSodium() +
                ", Price:" + this.getPrice();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + this.getTitle().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(!(obj instanceof BaseProduct))return false;
        return this.getTitle().equals(((BaseProduct)obj).getTitle());
    }
}
