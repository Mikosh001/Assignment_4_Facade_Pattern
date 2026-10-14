import facade.OrderFacade;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        OrderFacade facade = new OrderFacade();
        Map<String, Integer> cart = Map.of("BOOK", 1);

        demonstrate(facade, cart, "LOW_BALANCE");
        demonstrate(facade, cart, "NO_ADDRESS");
        demonstrate(facade, cart, "CUSTOMER");
    }

    private static void demonstrate(OrderFacade facade, Map<String, Integer> cart, String customerId) {
        System.out.println("Customer: " + customerId);
        try {
            System.out.println(facade.placeOrder(cart, customerId));
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Order failed: " + exception.getMessage());
        }
    }
}
