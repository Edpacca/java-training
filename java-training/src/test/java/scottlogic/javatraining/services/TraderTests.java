package scottlogic.javatraining.services;

import org.junit.jupiter.api.Test;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.Trade;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TraderTests {

    @Test
    void Should_StartTradingWithHighestPrice_WhenPassedNewBuyOrder() {
        UUID newOrderId = new UUID(1,1);
        UUID expectedMatchId = new UUID(2,2);
        Order newOrder = new Order(newOrderId,25f, 5f, Exchange.BUY, Market.CAD);
        Order expectedMatch = new Order(expectedMatchId,20f, 10f, Exchange.SELL, Market.CAD);

        List<Order> matchedOrders =  new ArrayList<Order>() {{
            add(expectedMatch);
            add(new Order(new UUID(3, 3),15f, 10f, Exchange.SELL, Market.CAD));
        }};
        Trader trader = new Trader();
        List<Trade> trades = trader.makeTrades(matchedOrders, newOrder);
        assertEquals(trades.size(), 1);
        assertEquals(trades.get(0).getBuyUserId(), newOrderId);
        assertEquals(trades.get(0).getSellUserId(), expectedMatchId);
    }

    @Test
    void Should_StartTradingWithLowestPrice_WhenPassedNewSellOrder() {
        UUID newOrderId = new UUID(1,1);
        UUID expectedMatchId = new UUID(2,2);
        Order newOrder = new Order(newOrderId,15f, 5f, Exchange.SELL, Market.CAD);
        Order expectedMatch = new Order(expectedMatchId,15f, 10f, Exchange.BUY, Market.CAD);

        List<Order> matchedOrders =  new ArrayList<Order>() {{
            add(expectedMatch);
            add(new Order(new UUID(3, 3),20f, 10f, Exchange.BUY, Market.CAD));
        }};

        Trader trader = new Trader();
        List<Trade> trades = trader.makeTrades(matchedOrders, newOrder);
        assertEquals(trades.size(), 1);
        assertEquals(trades.get(0).getBuyUserId(), expectedMatchId);
        assertEquals(trades.get(0).getSellUserId(), newOrderId);
    }

    @Test
    void Should_StopMakingTrades_WhenNewOrderIsComplete() {

        Order newOrder = new Order(new UUID(11,11),
                25f, 30f, Exchange.BUY, Market.CAD);

        List<Order> matchedOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1, 1),20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(2, 1),19f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(3, 1),18f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(4, 1),17f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5, 1),16f, 10f, Exchange.SELL, Market.CAD));
        }};

        assertFalse(newOrder.isComplete());

        Trader trader = new Trader();
        List<Trade> trades = trader.makeTrades(matchedOrders, newOrder);

        assertTrue(newOrder.isComplete());
        assertEquals(trades.size(), 3);
    }

    @Test
    void Should_ReduceNewOrderByQuantityInMatchedOrder_WhenMatchedOrderHasLowerQuantity() {
        Order newOrder = new Order(new UUID(1,1),
                25f, 15f, Exchange.BUY, Market.CAD);
        Order matchedOrder = new Order(new UUID(2, 2),
                25f, 10f, Exchange.SELL, Market.CAD);

        List<Order> matchedOrders =  new ArrayList<Order>() {{ add(matchedOrder); }};

        Trader trader = new Trader();
        List<Trade> trades = trader.makeTrades(matchedOrders, newOrder);
        assertEquals(newOrder.getQuantity(), 5f);
        assertEquals(matchedOrder.getQuantity(), 0f);
    }

    @Test
    void Should_ReduceNewOrderByQuantityInNewOrder_WhenNewOrderHasLowerQuantity() {
        Order newOrder = new Order(new UUID(1,1),
                25f, 10f, Exchange.BUY, Market.CAD);
        Order matchedOrder = new Order(new UUID(2, 2),
                25f, 15f, Exchange.SELL, Market.CAD);

        List<Order> matchedOrders =  new ArrayList<Order>() {{ add(matchedOrder); }};

        Trader trader = new Trader();
        List<Trade> trades = trader.makeTrades(matchedOrders, newOrder);
        assertEquals(newOrder.getQuantity(), 0f);
        assertEquals(matchedOrder.getQuantity(), 5f);
    }

    @Test
    void Should_TradeTheOldestOrderFirst_WhenPricesAreEqual_AndPassedBuyOrder() {
        Order newOrder = new Order(new UUID(11,11),
                25f, 70f, Exchange.BUY, Market.CAD);

        List<UUID> matchedIds = new ArrayList<UUID>() {{
            add(new UUID(0, 1));
            add(new UUID(1, 1));
            add(new UUID(2, 1));
            add(new UUID(3, 1));
            add(new UUID(4, 1));
            add(new UUID(5, 1));
            add(new UUID(6, 1));
        }};

        List<Order> matchedOrders =  new ArrayList<Order>() {{
            add(new Order(LocalDateTime.of(2021, 10, 1, 12, 0),
                    matchedIds.get(0), 20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 6, 1, 12, 0),
                    matchedIds.get(1), 20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 8, 1, 12, 0),
                    matchedIds.get(2), 20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 4, 1, 12, 0),
                    matchedIds.get(3), 20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 5, 1, 12, 0),
                    matchedIds.get(4), 20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 7, 1, 12, 0),
                    matchedIds.get(5), 20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 9, 1, 12, 0),
                    matchedIds.get(6), 20f, 10f, Exchange.SELL, Market.CAD));
        }};

        Trader trader = new Trader();
        List<Trade> trades = trader.makeTrades(matchedOrders, newOrder);
        // expected month order: 4, 5, 6, 7, 8, 9, 10
        // expected ID order:    3, 4, 1, 5, 2, 6, 0
        assertEquals(matchedIds.get(3), trades.get(0).getSellUserId());
        assertEquals(matchedIds.get(4), trades.get(1).getSellUserId());
        assertEquals(matchedIds.get(1), trades.get(2).getSellUserId());
        assertEquals(matchedIds.get(5), trades.get(3).getSellUserId());
        assertEquals(matchedIds.get(2), trades.get(4).getSellUserId());
        assertEquals(matchedIds.get(6), trades.get(5).getSellUserId());
        assertEquals(matchedIds.get(0), trades.get(6).getSellUserId());
    }

    @Test
    void Should_TradeTheOldestOrderFirst_WhenPricesAreEqual_AndPassedSellOrder() {
        Order newOrder = new Order(new UUID(11,11),
                15f, 70f, Exchange.SELL, Market.CAD);

        List<UUID> matchedIds = new ArrayList<UUID>() {{
            add(new UUID(0, 1));
            add(new UUID(1, 1));
            add(new UUID(2, 1));
            add(new UUID(3, 1));
            add(new UUID(4, 1));
            add(new UUID(5, 1));
            add(new UUID(6, 1));
        }};

        List<Order> matchedOrders =  new ArrayList<Order>() {{
            add(new Order(LocalDateTime.of(2021, 7, 1, 12, 0),
                    matchedIds.get(0), 20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 5, 1, 12, 0),
                    matchedIds.get(1), 20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 2, 1, 12, 0),
                    matchedIds.get(2), 20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 6, 1, 12, 0),
                    matchedIds.get(3), 20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 4, 1, 12, 0),
                    matchedIds.get(4), 20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 3, 1, 12, 0),
                    matchedIds.get(5), 20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(LocalDateTime.of(2021, 8, 1, 12, 0),
                    matchedIds.get(6), 20f, 10f, Exchange.BUY, Market.CAD));
        }};

        Trader trader = new Trader();
        List<Trade> trades = trader.makeTrades(matchedOrders, newOrder);
        // expected month order: 2, 3, 4, 5, 6, 7, 8
        // expected ID order:    2, 5, 4, 1, 3, 0, 6
        assertEquals(matchedIds.get(2), trades.get(0).getBuyUserId());
        assertEquals(matchedIds.get(5), trades.get(1).getBuyUserId());
        assertEquals(matchedIds.get(4), trades.get(2).getBuyUserId());
        assertEquals(matchedIds.get(1), trades.get(3).getBuyUserId());
        assertEquals(matchedIds.get(3), trades.get(4).getBuyUserId());
        assertEquals(matchedIds.get(0), trades.get(5).getBuyUserId());
        assertEquals(matchedIds.get(6), trades.get(6).getBuyUserId());
    }
}

