package scottlogic.javatraining.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor
public class Trade {
    @Id
    private UUID id;
    private LocalDateTime time;
    private UUID buyOrderId;
    private UUID buyUserId;
    private UUID sellOrderId;
    private UUID sellUserId;
    private Float quantity;
    private Float price;
    private Market market;

    public Trade(Order matchedOrder, Order newOrder) {
        validateTrade(matchedOrder, newOrder);
        this.price = matchedOrder.getPrice();
        this.market = matchedOrder.getMarket();
        this.id = UUID.randomUUID();
        this.time = LocalDateTime.now();
        this.quantity = Math.min(matchedOrder.getQuantity(), newOrder.getQuantity());

        Order buyOrder = matchedOrder.getExchange() == Exchange.BUY ? matchedOrder : newOrder;
        Order sellOrder = buyOrder == matchedOrder ? newOrder : matchedOrder;
        this.buyOrderId = buyOrder.getId();
        this.buyUserId = buyOrder.getUserId();
        this.sellOrderId = sellOrder.getId();
        this.sellUserId = sellOrder.getUserId();

        makeTrade(matchedOrder, newOrder);
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
