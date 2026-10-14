package facade;

import java.util.Map;

public class OrderFacade {
    private final InventoryService inventory = new InventoryService();
    private final PaymentProcessor payment = new PaymentProcessor();
    private final ShippingService shipping = new ShippingService();
    private final NotificationService notifications = new NotificationService();

    public String placeOrder(Map<String, Integer> cart, String customerId) {
        int amount = inventory.totalPrice(cart);
        Map<String, Integer> items = Map.copyOf(cart);
        inventory.reserve(items);

        boolean paid = false;
        String orderId = null;
        try {
            payment.charge(customerId, amount);
            paid = true;
            orderId = shipping.ship(items, customerId);
            return notifications.sendConfirmation(orderId, customerId, amount);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            if (orderId != null) {
                shipping.cancel(orderId);
            }
            if (paid) {
                payment.refund(customerId, amount);
            }
            inventory.release(items);
            throw exception;
        }
    }
}
