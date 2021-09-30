package scottlogic.javatraining.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class Order {
    @Id
    @NotNull
    private UUID id;
    @NotNull
    private UUID userId;
    @NotNull
    @PastOrPresent
    private LocalDateTime time;
    @NotNull
    private Exchange exchange;
    @NotNull
    private Market market;
    @NotNull
    @Positive
    private float price;
    @Setter
    @NotNull
    @DecimalMin("0.0")
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

    // Test constructor - inject localDateTime
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

    @Override
    public String toString() {
        return String.format(
                "Order[id=%s, %s %f (%s) at %f]",
                id.toString(), exchange.toString(), quantity, market.toString(), price);
    }
}
