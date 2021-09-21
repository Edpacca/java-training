package scottlogic.javatraining.services;

import scottlogic.javatraining.interfaces.ITrader;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.Trade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Trader implements ITrader {

    public List<Trade> makeTrades(List<Order> matchedOrders, Order newOrder) {

        List<Trade> trades = new ArrayList<>();

        if (newOrder.getExchange() == Exchange.SELL) {
            matchedOrders.sort(Comparator
                    .comparing(Order::getPrice)
                    .thenComparing(Order::getTime));
        } else {
            matchedOrders.sort(Comparator
                    .comparing(Order::getPrice)
                    .reversed()
                    .thenComparing(Order::getTime));
        }

        for (Order matchedOrder : matchedOrders) {
            Trade trade = new Trade(matchedOrder, newOrder);
            trades.add(trade);

            if (newOrder.isComplete())
                return trades;
        }

        return trades;
    }
}