package scottlogic.javatraining.interfaces;

import scottlogic.javatraining.models.Order;
import java.util.List;

public interface IMatcher {
    List<Order> getMatchingOrders(List<Order> ordersDb, Order newOrder);
}