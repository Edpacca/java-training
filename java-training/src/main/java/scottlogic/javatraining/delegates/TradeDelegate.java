package scottlogic.javatraining.delegates;

import org.springframework.stereotype.Component;
import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.UUID;

@Component("tradeDelegate")
public class TradeDelegate implements ITradeDelegate {
    @Override
    public List<Trade> getDbTrades() { throw new UnsupportedOperationException(); }

    @Override
    public Trade getTrade(UUID id) {
        throw new UnsupportedOperationException();
    }
}
