package facade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class NotificationService {
    private final Map<String, String> confirmations = new HashMap<>();

    public String sendConfirmation(String orderId, String customerId, int amount) {
        if (orderId == null || orderId.isBlank() || customerId == null || customerId.isBlank() || amount <= 0) {
            throw new IllegalArgumentException("Confirmation needs an order, customer, and positive amount.");
        }
        if (confirmations.containsKey(orderId)) {
            throw new IllegalStateException("Confirmation already exists for " + orderId + ".");
        }
        String message = "Order " + orderId + " confirmed for " + customerId
                + ", total: " + BigDecimal.valueOf(amount, 2).toPlainString() + ".";
        confirmations.put(orderId, message);
        return message;
    }
}
