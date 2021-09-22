package scottlogic.javatraining.models;

import java.util.UUID;

public class OrderRequest {
    public UUID userId;
    public Exchange exchange;
    public Market market;
    public float price;
    public float quantity;
}
