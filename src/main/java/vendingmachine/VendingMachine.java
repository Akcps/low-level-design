package vendingmachine;

import java.util.*;

public class VendingMachine {
    private static final int SLOT_CAPACITY = 10;
    private final List<Slot> slots;
    private final Map<Coins, Integer> coins;
    private final int slotsCount;

    public VendingMachine(int slotsCount) {
        this.slotsCount = slotsCount;
        this.slots = new ArrayList<>();
        this.coins = new HashMap<>();
    }

    @Override
    public String toString() {
        return "VendingMachine{" +
                "slots=" + slots +
                ", coins=" + coins +
                ", slotsCount=" + slotsCount +
                '}';
    }

    public void configure(List<Product> products) {
        int slotNumber = 1;
        for (Product p : products) {
            Slot s = new Slot(slotNumber++, p, SLOT_CAPACITY);
            this.slots.add(s);
        }
    }

    public void refillProduct(Product product) {
        this.slots
                .stream()
                .filter(p -> p.getProduct().equals(product))
                .findAny().
                ifPresent(matchedSlot -> matchedSlot.refill(SLOT_CAPACITY));
    }

    public void dispenseOrder(Order order) {
        for (Product product : order.productQty.keySet()) {
            this.slots.stream()
                    .filter(p -> p.getProduct().equals(product))
                    .findAny()
                    .ifPresent(matchedSlot -> matchedSlot.reduceQty(order.productQty.get(product)));
        }
    }

    public void addCoinsFromPayment(Payment payment) {
        for (Coins c : payment.coinQty.keySet()) {
            this.coins.put(c, this.coins.getOrDefault(c, 0) + payment.coinQty.get(c));
        }
    }

    public void refillCoin(Coins coin, int qty) {
        this.coins.put(coin, this.coins.getOrDefault(coin, 0) + qty);
    }

    public boolean areItemsAvailable(Order order) {
        for (Product product : order.productQty.keySet()) {
            final Optional<Slot> matchedSlot = this.slots
                    .stream()
                    .filter(p -> p.getProduct().equals(product))
                    .findAny();
            if (matchedSlot.isPresent()) {
                boolean available = matchedSlot.get().getAvailableQty() >= order.productQty.get(product);
                if (!available) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean processOrder(Order order, Payment payment) {

        boolean itemsAvailable = areItemsAvailable(order);
        if (!itemsAvailable) {
            return false;
        }

        if (order.getTotalAmount() > payment.getTotalAmount()) {
            System.out.println("Payment amount is less than order amount");
            return false;
        } else if (order.getTotalAmount() == payment.getTotalAmount()) {
            // dispense items
            dispenseOrder(order);
            // add payment coin to coins
            addCoinsFromPayment(payment);
            return true;
        } else {
            // Check whether change can be dispensed and dispense change
            System.out.println(this);
            List<Coins> change = new ArrayList<>();
            Coins[] coinArray = getCoinsArray();
            boolean[] used = new boolean[coinArray.length];
            boolean possible = canDispenseChange(coinArray, 0, payment.getTotalAmount() - order.getTotalAmount(), change);
            if (possible) {
                System.out.println("Change to be dispensed" + change);
                dispenseChange(change);
                // dispense items
                dispenseOrder(order);
                // add payment coin to coins
                addCoinsFromPayment(payment);
                return true;
            } else {
                System.out.println("Unable to dispense change");
                return false;
            }
        }
    }

    private Coins[] getCoinsArray() {
        int size = 0;
        for (Coins c : coins.keySet()) {
            size += coins.get(c);
        }
        Coins[] coinArray = new Coins[size];
        int index = 0;
        for (Coins c : coins.keySet()) {
            for (int i = 0; i < coins.get(c); i++) {
                coinArray[index++] = c;
            }
        }
        return coinArray;
    }

    private void dispenseChange(List<Coins> change) {
        for (Coins coin : change) {
            int left = coins.get(coin) - 1;
            if (left > 0) {
                coins.put(coin, left);
            }
        }
    }

    public boolean canDispenseChange(Coins[] coins, int index, int amount, List<Coins> result) {
        if (amount == 0) {
            return true;
        }

        if (amount < 0) {
            return false;
        }

        for (int i = index; i < coins.length; i++) {
            result.add(coins[i]);
            if (canDispenseChange(coins, i + 1, amount - coins[i].getValue(), result)) {
                return true;
            }
            result.remove(result.size() - 1);
        }
        return false;
    }
}