package vendingmachine;

public class Slot {
    private final int number;
    private final Product product;
    private int availableQty;

    public Slot(int number, Product product, int availableQty) {
        this.number = number;
        this.product = product;
        this.availableQty = availableQty;
    }

    public int getNumber() {
        return number;
    }

    public Product getProduct() {
        return product;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void refill(int qty) {
        this.availableQty += qty;
    }

    public void reduceQty(int qty) {
        this.availableQty -= qty;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "number=" + number +
                ", product=" + product +
                ", availableQty=" + availableQty +
                '}';
    }
}
