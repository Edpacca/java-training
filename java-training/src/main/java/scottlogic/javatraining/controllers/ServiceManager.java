package scottlogic.javatraining.controllers;

import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.Trade;
import scottlogic.javatraining.services.Matcher;
import scottlogic.javatraining.services.Trader;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager {

      public static void handleNewOrder(Order order, List<Order> orderDb, List<Trade> tradeDb) {
        List<Order> matchedOrders = Matcher.getMatchingOrders(orderDb, order);
        List<Trade> newTrades = Trader.makeTrades(matchedOrders, order);
        tradeDb.addAll(newTrades);
        if (!order.isComplete()) orderDb.add(order);
        removeCompletedOrders(orderDb);
    }

    private static void removeCompletedOrders(List<Order> orderDb) {
        List<Order> completed = new ArrayList<>();
        orderDb.forEach(order -> {
            if (order.isComplete()) completed.add(order);
        });
        orderDb.removeAll(completed);
    }
}
