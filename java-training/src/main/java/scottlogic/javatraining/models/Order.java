package scottlogic.javatraining.models;

import java.util.*;

public class Order {
    private final Date time = new Date();
    private final UUID id = UUID.randomUUID();
    private UUID userId;
    private Float quantity;
    private Float price;
    private Exchange exchange;
    private Market market;
    private boolean isComplete = false;

    public Order(UUID userId, float quantity, float price, Exchange exchange, Market market) {
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.exchange = exchange;
        this.market = market;
    }

    public float getQuantity() {
        return quantity;
    }

    public void reduceQuantity(float quantity) {
        if (this.quantity < quantity) {
            throw new Error("trade quantity is more than order quantity. Cannot make trade");
        }
        this.quantity -= quantity;
        if (this.quantity == 0) {
            this.isComplete = true;
        }
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public float getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Market getMarket() {
        return market;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public Date getTime() {
        return time;
    }
}
