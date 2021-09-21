package scottlogic.javatraining.interfaces;

import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.Trade;

import java.util.List;

public interface ITrader {
    List<Trade> makeTrades(List<Order> matchedOrders, Order newOrder);
}
