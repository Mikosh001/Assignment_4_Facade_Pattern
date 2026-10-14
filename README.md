# Assignment 4 | Facade Pattern

Berdibek Meiirbek, SE-2528  
Option B: Online Order Processing  
Repository: https://github.com/Mikosh001/Assignment_4_Facade_Pattern

`Main` calls only `OrderFacade.placeOrder(cart, customerId)`. The facade calculates the total, reserves stock, charges the customer, creates a shipment, and records a confirmation. A failed step reverses the completed steps.

| Class | Responsibility |
| --- | --- |
| `OrderFacade` | Coordinates order placement and rollback. |
| `InventoryService` | Calculates prices, checks stock, reserves and releases items. |
| `PaymentProcessor` | Charges and refunds customer balances. |
| `ShippingService` | Creates and cancels shipment records. |
| `NotificationService` | Formats and stores order confirmations. |

The services simulate an order system in memory. Amounts use integer cents. They have public operations and no references to the facade, so advanced clients can use them directly.

## Run

Use JDK 17 from the project folder:

```text
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main
```

In IntelliJ IDEA, open this folder, select JDK 17, mark `src` as Sources Root if needed, and run `Main.main()`. No program arguments are required.

The same facade processes three attempts for the only available book:

```text
Customer: LOW_BALANCE
Order failed: Insufficient balance for LOW_BALANCE.
Customer: NO_ADDRESS
Order failed: No delivery address for NO_ADDRESS.
Customer: CUSTOMER
Order ORD-1 confirmed for CUSTOMER, total: 10.00.
```

The first failure releases stock. The second refunds the payment and releases stock. The final order succeeds because the book was restored. If confirmation fails after shipment creation, rollback also cancels that shipment.

`report.pdf` contains the UML and five annotated Clean Code examples. Diagram source: `uml/facade.puml`.

Reference: [Refactoring.Guru - Facade](https://refactoring.guru/design-patterns/facade). OpenAI Codex assisted with implementation, report drafting, and verification.
