package scottlogic.javatraining.services;

import java.util.List;

import jdk.vm.ci.code.site.Mark;
import scottlogic.javatraining.interfaces.IFilterCondition;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Order;

import static java.util.stream.Collectors.toList;

public class Matcher {

    public static List<Order> getMatchingOrders(List<Order> ordersDb, Order newOrder) {
        Exchange matchedExchange = getOppositeExchange(newOrder);
        Market matchedMarket = newOrder.getMarket();
        IFilterCondition priceFilter = matchedExchange == Exchange.BUY
                ? Matcher::isPriceMatch_dbBuyOrder
                : Matcher::isPriceMatch_dbSellOrder;

        return ordersDb.stream()
                .filter(order -> order.getMarket() == matchedMarket)
                .filter(order -> order.getExchange() == matchedExchange)
                .filter(order -> priceFilter.isMatch(order, newOrder))
                .filter(order -> order.getUserId() != newOrder.getUserId())
                .collect(toList());
    }

    private static Exchange getOppositeExchange(Order newOrder) {
        return newOrder.getExchange() == Exchange.BUY
                ? Exchange.SELL : Exchange.BUY;
    }

    private static boolean isPriceMatch_dbBuyOrder(Order dbBuyOrder, Order newSellOrder) {
        return dbBuyOrder.getPrice() <= newSellOrder.getPrice();
    }

    private static boolean isPriceMatch_dbSellOrder(Order dbSellOrder, Order newBuyOrder) {
        return dbSellOrder.getPrice() >= newBuyOrder.getPrice();
    }
}