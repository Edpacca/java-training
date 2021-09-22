package scottlogic.javatraining.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Order {
    @Id
    private final UUID id;

    private final UUID userId;
    private final LocalDateTime time;
    private final Exchange exchange;
    private final Market market;
    private final float price;

    @Setter
    private float quantity;

    public Order(UUID userId, float price, float quantity, Exchange exchange, Market market) {
        this.time = LocalDateTime.now();
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.price = price;
        this.quantity = quantity;
        this.exchange = exchange;
        this.market = market;
    }

    public Order(LocalDateTime time, UUID userId, float price, float quantity, Exchange exchange, Market market) {
        this.time = time;
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.price = price;
        this.quantity = quantity;
        this.exchange = exchange;
        this.market = market;
    }

    public void reduceQuantity(float quantity) {
        if (this.quantity < quantity) {
            throw new Error("trade quantity is more than order quantity. Cannot make trade");
        }
        this.quantity -= quantity;
    }

    @JsonIgnore
    public boolean isComplete() {
        return quantity == 0;
    }
}