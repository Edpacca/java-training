package scottlogic.javatraining.models;

import jdk.vm.ci.meta.Local;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

@Getter
public class Trade {
    private final LocalDate time;
    private final UUID id;
    private final UUID buyOrderId;
    private final UUID buyUserId;
    private final UUID sellOrderId;
    private final UUID sellUserId;
    private final Float quantity;
    private final Float price;
    private final Market market;

    public Trade(Order order1, Order order2) {
        validateTrade(order1, order2);
        this.price = order1.getPrice();
        this.market = order1.getMarket();
        this.id = UUID.randomUUID();
        this.time = LocalDate.now();
        this.quantity = Math.min(order1.getQuantity(), order2.getQuantity());

        Order buyOrder = order1.getExchange() == Exchange.BUY ? order1 : order2;
        Order sellOrder = buyOrder == order1 ? order2 : order1;
        this.buyOrderId = buyOrder.getId();
        this.buyUserId = buyOrder.getUserId();
        this.sellOrderId = sellOrder.getId();
        this.sellUserId = sellOrder.getUserId();

        makeTrade(order1, order2);
    }

    private void validateTrade(Order order1, Order order2) {
        if (order1.getId() == order2.getId()) {
            throw new Error("Order cannot be traded with itself");
        }
        if (order1.getUserId() == order2.getUserId()) {
            throw new Error("Orders created by same user cannot be traded");
        }
        if (order1.getExchange() == order2.getExchange()) {
            throw new Error("Orders with same Exchange type cannot be traded");
        }
        if (order1.getMarket() != order2.getMarket()) {
            throw new Error("Orders with different Market type cannot be traded");
        }
    }

    private void makeTrade(Order order1, Order order2) {
        order1.reduceQuantity(this.quantity);
        order2.reduceQuantity(this.quantity);
    }
}
