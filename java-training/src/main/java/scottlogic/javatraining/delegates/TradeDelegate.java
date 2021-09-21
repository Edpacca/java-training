package scottlogic.javatraining.delegates;

import org.springframework.stereotype.Component;
import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.UUID;

@Component("tradeDelegate")
public class TradeDelegate implements ITradeDelegate {
    @Override
    public List<Trade> getDbTrades() {
        return null;
    }

    @Override
    public Trade getTrade(UUID id) {
        return null;
    }
}
