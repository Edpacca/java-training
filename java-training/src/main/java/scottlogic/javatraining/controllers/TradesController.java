package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scottlogic.javatraining.interfaces.ITradeService;
import scottlogic.javatraining.models.Trade;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("trades")
public class TradesController {

    @Autowired
    private ITradeService tradeService;

    @GetMapping
    public ResponseEntity<List<Trade>> getTrades() {
        List<Trade> trades = tradeService.getDbTrades();
        return trades == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(trades);
    }

    @GetMapping (path="/{id}")
    public ResponseEntity<Trade> getTrade(@PathVariable final UUID id) {
        Trade requestedTrade = tradeService.getTrade(id);

        return requestedTrade == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(requestedTrade);
    }
}
