package scottlogic.javatraining.models;

import java.util.UUID;

public class OrderRequest {
    public UUID userId;
    public Exchange exchange;
    public Market market;
    public float price;
    public float quantity;

    public OrderRequest(UUID userId, float price, float quantity, Exchange exchange, Market market) {
        this.userId = userId;
        this.price = price;
        this.quantity = quantity;
        this.exchange = exchange;
        this.market = market;
    }
}
