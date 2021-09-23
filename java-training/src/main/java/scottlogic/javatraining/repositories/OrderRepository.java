package scottlogic.javatraining.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Order;

public interface OrderRepository extends MongoRepository<Order, UUID> {
    Optional<Order> findById(UUID id);
    List<Order> findByUserId(UUID id);
    List<Order> findByPrice(Float price);
    List<Order> findByQuantity(Float quantity);
    List<Order> findByMarket(Market market);
    List<Order> findByExchange(Exchange exchange);
}
