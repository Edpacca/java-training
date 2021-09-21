package scottlogic.javatraining.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderDTO {
    public LocalDateTime time;
    public UUID id;
    public UUID userId;
    public float price;
    public float quantity;
    public Exchange exchange;
    public Market market;
}
