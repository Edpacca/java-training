package scottlogic.javatraining.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class OrderRequest {
    @NotNull (message = "User Id must be specified")
    public UUID userId;
    @NotNull (message = "Exchange (BUY/SELL) must be specified")
    public Exchange exchange;
    @NotNull (message = "Market must be specified")
    public Market market;
    @Positive (message = "price must be positive")
    public float price;
    @Positive (message = "quantity must be positive")
    public float quantity;

    public OrderRequest(UUID userId, float price, float quantity, Exchange exchange, Market market) {
        this.userId = userId;
        this.price = price;
        this.quantity = quantity;
        this.exchange = exchange;
        this.market = market;
    }
}
