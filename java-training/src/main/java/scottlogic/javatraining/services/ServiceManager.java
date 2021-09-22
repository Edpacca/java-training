package scottlogic.javatraining.services;

import scottlogic.javatraining.interfaces.IMatcher;
import scottlogic.javatraining.interfaces.ITrader;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceManager {

    private final IMatcher matcher;
    private final ITrader trader;

    public ServiceManager(IMatcher matcher, ITrader trader) {
        this.matcher = matcher;
        this.trader = trader;
    }

    public void handleNewOrder(Order order, List<Order> orderDb, List<Trade> tradeDb) {
        List<Order> matchedOrders = matcher.getMatchingOrders(orderDb, order);

        if (matchedOrders.size() > 0) {
            List<Trade> newTrades = trader.makeTrades(matchedOrders, order);
            tradeDb.addAll(newTrades);
        }
        if (!order.isComplete()) orderDb.add(order);

        removeCompletedOrders(orderDb);
    }

    private void removeCompletedOrders(List<Order> orderDb) {
        List<Order> completed = orderDb.stream()
                .filter(Order::isComplete)
                .collect(Collectors.toList());

        orderDb.removeAll(completed);
    }
}
