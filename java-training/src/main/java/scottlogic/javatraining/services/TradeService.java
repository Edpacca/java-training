package scottlogic.javatraining.services;

import scottlogic.javatraining.interfaces.ITradeService;
import scottlogic.javatraining.models.Trade;
import scottlogic.javatraining.repositories.TradeRepository;

import java.util.List;
import java.util.UUID;

public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getDbTrades() {
        return tradeRepository.findAll();
    }

    public Trade getTrade(UUID id) {
        return tradeRepository.findById(id).orElse(null);
    }
}
