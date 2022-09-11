package vendingmachine;

public enum Coins {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10);

    public int value;

    Coins(int c) {
        this.value = c;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Coins{" +
                "value=" + value +
                '}';
    }
}
