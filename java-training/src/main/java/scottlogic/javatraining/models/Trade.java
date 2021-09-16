package scottlogic.javatraining.models;

import java.util.*;

public class Trade {
    private final Date time = new Date();
    private final UUID id = UUID.randomUUID();
    private UUID buyOrderId;
    private UUID buyUserId;
    private UUID sellOrderId;
    private UUID sellUserId;
    private Float quantity;
    private Float price;
    private Market market;

    public Trade(Order order1, Order order2) {
        this.price = order1.getPrice();
        this.market = order1.getMarket();
        this.quantity = Math.min(order1.getQuantity(), order2.getQuantity());
        parseOrders(order1, order2);
        makeTrade(order1, order2);
    }

    private void makeTrade(Order order1, Order order2) {
        order1.reduceQuantity(this.quantity);
        order2.reduceQuantity(this.quantity);
    }

    private void parseOrders(Order order1, Order order2) {
        Order buyOrder = order1.getExchange() == Exchange.BUY ? order1 : order2;
        Order sellOrder = buyOrder == order1 ? order2 : order1;
        this.buyOrderId = buyOrder.getId();
        this.buyUserId = buyOrder.getUserId();
        this.sellOrderId = sellOrder.getId();
        this.sellUserId = sellOrder.getUserId();
    }

    public float getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }

    public UUID[] getBuyIds() {
        return new UUID[]{ buyOrderId, buyUserId };
    }

    public UUID[] getSellIds() {
        return new UUID[]{ sellOrderId, sellUserId };
    }

    public Market getMarket() {
        return market;
    }

    public Date getTime() {
        return time;
    }
}
