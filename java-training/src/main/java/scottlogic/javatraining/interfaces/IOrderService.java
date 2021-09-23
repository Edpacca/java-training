package scottlogic.javatraining.interfaces;

import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.OrderRequest;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    List<Order> getDbOrders();
    Order getOrder(UUID id);
    Order postOrder(OrderRequest order);
    void deleteOrder(UUID id);
}
