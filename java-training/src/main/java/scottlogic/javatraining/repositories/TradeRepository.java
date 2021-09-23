package scottlogic.javatraining.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TradeRepository extends MongoRepository<Trade, UUID> {
    Optional<Trade> findById(UUID id);
    List<Trade> findByBuyUserId(UUID id);
    List<Trade> findBySellUserId(UUID id);
    List<Trade> findByPrice(Float price);
    List<Trade> findByQuantity(Float quantity);
    List<Trade> findByMarket(Market market);
}
