package facade;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private final Map<String, Integer> stock = new HashMap<>(Map.of("BOOK", 1, "PEN", 10));
    private final Map<String, Integer> prices = Map.of("BOOK", 1000, "PEN", 100);

    public int totalPrice(Map<String, Integer> cart) {
        validateCart(cart);
        int total = 0;
        try {
            for (var item : cart.entrySet()) {
                total = Math.addExact(total, Math.multiplyExact(prices.get(item.getKey()), item.getValue()));
            }
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("Order total is too large.", exception);
        }
        return total;
    }

    public void reserve(Map<String, Integer> cart) {
        validateCart(cart);
        for (var item : cart.entrySet()) {
            if (available(item.getKey()) < item.getValue()) {
                throw new IllegalStateException("Not enough stock for " + item.getKey() + ".");
            }
        }
        for (var item : cart.entrySet()) {
            stock.put(item.getKey(), stock.get(item.getKey()) - item.getValue());
        }
    }

    public void release(Map<String, Integer> cart) {
        validateCart(cart);
        for (var item : cart.entrySet()) {
            stock.put(item.getKey(), Math.addExact(stock.get(item.getKey()), item.getValue()));
        }
    }

    public int available(String productId) {
        if (productId == null || !stock.containsKey(productId)) {
            throw new IllegalArgumentException("Unknown product: " + productId + ".");
        }
        return stock.get(productId);
    }

    private void validateCart(Map<String, Integer> cart) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cart must contain at least one item.");
        }
        for (var item : cart.entrySet()) {
            available(item.getKey());
            if (item.getValue() == null || item.getValue() <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for " + item.getKey() + ".");
            }
        }
    }
}
