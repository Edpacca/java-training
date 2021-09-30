package scottlogic.javatraining.interfaces;

import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.UUID;

public interface ITradeService {
    List<Trade> getDbTrades();
    Trade getTrade(UUID id);
}
