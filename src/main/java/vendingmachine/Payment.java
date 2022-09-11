package vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class Payment {
    Map<Coins, Integer> coinQty;

    public Payment() {
        this.coinQty = new HashMap<>();
    }

    public void addCoin(Coins coin) {
        coinQty.put(coin, coinQty.getOrDefault(coin, 0) + 1);
    }

    public int getTotalAmount() {
        int amount = 0;
        for (Coins c : coinQty.keySet()) {
            amount += c.getValue() * coinQty.get(c);
        }
        return amount;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "coinQty=" + coinQty +
                "total=" + getTotalAmount() +
                '}';
    }
}
