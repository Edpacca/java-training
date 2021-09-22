package scottlogic.javatraining.delegates;

import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.UUID;

public interface ITradeDelegate {
    List<Trade> getDbTrades();
    Trade getTrade(UUID id);
}
