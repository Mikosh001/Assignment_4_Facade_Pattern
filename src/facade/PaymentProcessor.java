package facade;

import java.util.HashMap;
import java.util.Map;

public class PaymentProcessor {
    private final Map<String, Integer> balances = new HashMap<>(Map.of(
            "CUSTOMER", 5000, "LOW_BALANCE", 500, "NO_ADDRESS", 5000));

    public void charge(String customerId, int amount) {
        validateAmount(amount);
        int balance = balanceOf(customerId);
        if (balance < amount) {
            throw new IllegalStateException("Insufficient balance for " + customerId + ".");
        }
        balances.put(customerId, balance - amount);
    }

    public void refund(String customerId, int amount) {
        validateAmount(amount);
        balances.put(customerId, Math.addExact(balanceOf(customerId), amount));
    }

    public int balanceOf(String customerId) {
        if (customerId == null || !balances.containsKey(customerId)) {
            throw new IllegalArgumentException("Unknown customer: " + customerId + ".");
        }
        return balances.get(customerId);
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }
    }
}
