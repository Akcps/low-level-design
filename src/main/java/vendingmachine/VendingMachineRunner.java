package vendingmachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineRunner {
    public static void main(String[] args) {

        // Add products
        Product coke = new Product(1, "Coke", 5);
        Product pepsi = new Product(2, "Pepsi", 10);
        Product soda = new Product(3, "Soda", 15);

        List<Product> products = new ArrayList<>();
        products.add(coke);
        products.add(pepsi);
        products.add(soda);

        System.out.println("Products in the Vending Machine: " + products);


        // Configure vending machine
        VendingMachine vendingMachine = new VendingMachine(3);
        vendingMachine.configure(products);

        // Add coins to vending machine
        vendingMachine.refillCoin(Coins.ONE, 5);
        vendingMachine.refillCoin(Coins.TWO, 5);
        vendingMachine.refillCoin(Coins.FIVE, 5);
        vendingMachine.refillCoin(Coins.TEN, 5);

        System.out.println("<Vending Machine> : " + vendingMachine);


        // User select items
        Order order = new Order();
        order.addProduct(coke);
        order.addProduct(pepsi);

        System.out.println("Order Details : " + order);


        // user payment
        Payment payment = new Payment();
        payment.addCoin(Coins.TEN);
        payment.addCoin(Coins.TEN);

        System.out.println("User Payment Details: " + payment);

        boolean processed = vendingMachine.processOrder(order, payment);
        if (processed) {
            System.out.println("The order was processed.");
        } else {
            System.out.println("The order was unable to be processed.");
        }
        System.out.println("Vending Machine :" + vendingMachine);
    }
}
