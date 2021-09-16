package scottlogic.javatraining.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Order {
    private final Date time = new Date();
    private final UUID id = UUID.randomUUID();
    private boolean isComplete = false;

    private final UUID userId;
    private final Float price;

    @NonNull @Setter
    private Float quantity;

    private final Exchange exchange;
    private final Market market;

    public void reduceQuantity(float quantity) {
        if (this.quantity < quantity) {
            throw new Error("trade quantity is more than order quantity. Cannot make trade");
        }
        this.quantity -= quantity;
        if (this.quantity == 0) {
            this.isComplete = true;
        }
    }
}
