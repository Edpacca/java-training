package scottlogic.javatraining.delegates;

import org.springframework.stereotype.Component;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.OrderRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("orderDelegate")
public class OrderDelegate implements IOrderDelegate {

    public List<Order> getDbOrders() {
        return new ArrayList<Order>() {{
            add(new Order(UUID.randomUUID(),80f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(UUID.randomUUID(),70f, 34f, Exchange.BUY, Market.USD));
            add(new Order(UUID.randomUUID(),66f, 52f, Exchange.BUY, Market.JPY));
            add(new Order(UUID.randomUUID(),54f, 17f, Exchange.SELL, Market.GBP));
            add(new Order(UUID.randomUUID(),102f, 14f, Exchange.SELL, Market.CHF));
            add(new Order(UUID.randomUUID(),99f, 20f, Exchange.SELL, Market.EUR));
        }};
    }

    public Order getOrder(UUID id) {
        return new Order(UUID.randomUUID(),80f, 10f, Exchange.BUY, Market.CAD);
    }

    public Order postOrder(OrderRequest orderRequest) {
        Order newOrder = new Order(
                orderRequest.userId,
                orderRequest.price,
                orderRequest.quantity,
                orderRequest.exchange,
                orderRequest.market
        );
        return newOrder;
    }
}
