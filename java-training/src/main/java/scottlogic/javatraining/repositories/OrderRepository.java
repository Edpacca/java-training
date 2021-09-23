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
}
