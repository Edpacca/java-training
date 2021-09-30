package scottlogic.javatraining.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import scottlogic.javatraining.models.Trade;

import java.util.Optional;
import java.util.UUID;

public interface TradeRepository extends MongoRepository<Trade, UUID> {
    Optional<Trade> findById(UUID id);
}
