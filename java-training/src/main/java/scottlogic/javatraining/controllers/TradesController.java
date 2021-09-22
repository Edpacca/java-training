package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import scottlogic.javatraining.delegates.ITradeDelegate;
import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.UUID;

@RestController
public class TradesController {

    @Autowired
    private ITradeDelegate tradeDelegate;

    @GetMapping(path ="/trades")
    public ResponseEntity<List<Trade>> getTrades() {
        List<Trade> trades = tradeDelegate.getDbTrades();
        return trades == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(trades);
    }

    @GetMapping (path="/trade/{id}")
    public ResponseEntity<Trade> getTrade(@PathVariable final UUID id) {
        Trade requestedTrade = tradeDelegate.getTrade(id);

        return requestedTrade == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(requestedTrade);
    }
}
