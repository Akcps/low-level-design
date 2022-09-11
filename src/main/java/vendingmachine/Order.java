package vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class Order {
    Map<Product, Integer> productQty;

    public Order() {
        this.productQty = new HashMap<>();
    }

    public void addProduct(Product product) {
        productQty.put(product, productQty.getOrDefault(product, 0) + 1);
    }

    public int getTotalAmount() {
        int amount = 0;
        for (Product p : productQty.keySet()) {
            amount += p.getPrice() * productQty.get(p);
        }
        return amount;
    }


    @Override
    public String toString() {
        return "Order{" +
                "productQty=" + productQty +
                "total=" + getTotalAmount() +
                '}';
    }
}