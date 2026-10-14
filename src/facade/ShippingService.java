package facade;

import java.util.HashMap;
import java.util.Map;

public class ShippingService {
    private final Map<String, String> addresses = Map.of(
            "CUSTOMER", "Astana", "LOW_BALANCE", "Almaty");
    private final Map<String, String> shipments = new HashMap<>();
    private int nextOrderNumber = 1;

    public String ship(Map<String, Integer> cart, String customerId) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cannot ship an empty cart.");
        }
        if (customerId == null || !addresses.containsKey(customerId)) {
            throw new IllegalStateException("No delivery address for " + customerId + ".");
        }
        String orderId = "ORD-" + nextOrderNumber++;
        shipments.put(orderId, customerId + " | " + addresses.get(customerId) + " | " + cart);
        return orderId;
    }

    public void cancel(String orderId) {
        shipments.remove(orderId);
    }
}
