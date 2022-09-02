package businessLayer;

import java.io.Serializable;
import java.util.ArrayList;

public class CompositeProduct extends MenuItem implements Serializable {
    private ArrayList<MenuItem> subItems;

    public CompositeProduct(String title){
        super(title,0,0,0,0,0,0);
        this.subItems = new ArrayList<>();
    }

    public void addItem(MenuItem item){
        this.subItems.add(item);
        this.setRating( (this.getRating()*(this.subItems.size()-1) + item.getRating()) /this.subItems.size());
        this.setCalories(this.getCalories() + item.getCalories());
        this.setProteins(this.getProteins() + item.getProteins());
        this.setFats(this.getFats() + item.getFats());
        this.setSodium(this.getSodium() + item.getSodium());
        this.setPrice(this.getPrice() + item.getPrice());
    }

    public void removeItem(MenuItem item){
        this.subItems.remove(item);
        this.setRating( (this.getRating()*(this.subItems.size()+1) - item.getRating()) /this.subItems.size());
        this.setCalories(this.getCalories()-item.getCalories());
        this.setProteins(this.getProteins() - item.getProteins());
        this.setFats(this.getFats() - item.getFats());
        this.setSodium(this.getSodium() - item.getSodium());
        this.setPrice(this.getPrice() - item.getPrice());

    }

    public CompositeProduct findProducts(String category){
        if(this.getTitle().equals(category))return this;
        for(MenuItem item: this.subItems){
            if(item instanceof CompositeProduct && ((CompositeProduct) item).getTitle().equals(category))
                return (CompositeProduct) item;
            else if(item instanceof CompositeProduct && ((CompositeProduct) item).findProducts(category)!=null)
                return ((CompositeProduct) item).findProducts(category);
        }
        return null;
    }

    @Override
    public int computePrice() {
        return this.getPrice();
    }

    public ArrayList<MenuItem> getSubItems() {
        return subItems;
    }


    @Override
    public String toString() {
        return "Title:" + this.getTitle() + ", Rating:" + this.getRating() + ", Calories:" + this.getCalories() +
                ", Proteins:" + this.getProteins() + ", Fats:" + this.getFats() + ", Sodium:" + this.getSodium() +
                ", Price:" + this.getPrice();
    }
}
