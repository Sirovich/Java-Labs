package Models;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;
    private int volume;

    public Cart(){
        products = new ArrayList<Product>();
    }

    public void addProduct(Product product){
        if (products.size() == volume) {
            //proccess the situation
        }

        products.add(product);
    }
}
