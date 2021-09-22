package scottlogic.javatraining.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ServiceManagerTests {

    @Mock
    Matcher mockMatcher = Mockito.mock(Matcher.class);

    @Mock
    Trader mockTrader = Mockito.mock(Trader.class);

    @Test
    void Should_AddTradesToTradeDb_WhenPassedNewOrderWithMatches() {

        Order newOrder = (new Order(
                new UUID(7,7),40f, 30f, Exchange.BUY, Market.CAD));

        List<Trade> tradeDb = new ArrayList<>();

        List<Order> orderDb = new ArrayList<Order>() {{
            add(new Order(new UUID(4,4),25f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),30f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),35f, 10f, Exchange.SELL, Market.CAD));
        }};

        List<Trade> trades = new ArrayList<Trade>() {{
            add(new Trade(orderDb.get(0), newOrder));
            add(new Trade(orderDb.get(1), newOrder));
            add(new Trade(orderDb.get(2), newOrder));
        }};

        Mockito.when(mockMatcher.getMatchingOrders(orderDb, newOrder)).thenReturn(orderDb);
        Mockito.when(mockTrader.makeTrades(orderDb, newOrder)).thenReturn(trades);
        ServiceManager service = new ServiceManager(mockMatcher, mockTrader);
        service.handleNewOrder(newOrder, orderDb, tradeDb);
        assertEquals(3, tradeDb.size());
    }

    @Test
    void Should_AddInCompleteOrderToOrderDb_IfOrderHasNoMatches() {
        Order newOrder = (new Order(
                new UUID(7,7),40f, 40f, Exchange.BUY, Market.CAD));
        List<Order> orderDb = new ArrayList<>();
        List<Trade> tradeDb = new ArrayList<>();

        Mockito.when(mockMatcher.getMatchingOrders(orderDb, newOrder)).thenReturn(orderDb);
        ServiceManager service = new ServiceManager(mockMatcher, mockTrader);
        service.handleNewOrder(newOrder, orderDb, tradeDb);
        assertTrue(orderDb.contains(newOrder));
    }

    @Test
    void Should_AddInCompleteOrderToOrderDb_IfOrderHasMatchesButRemainedInComplete() {
        Order newOrder = new Order(
                new UUID(7,7),40f, 40f, Exchange.BUY, Market.CAD);

        List<Order> orderDb = new ArrayList<Order>() {{
            add(new Order(new UUID(4,4),25f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),30f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),35f, 10f, Exchange.SELL, Market.CAD));
        }};

        List<Trade> tradeDb = new ArrayList<>();

        List<Trade> trades = new ArrayList<Trade>() {{
            add(new Trade(orderDb.get(0), newOrder));
            add(new Trade(orderDb.get(1), newOrder));
            add(new Trade(orderDb.get(2), newOrder));
        }};

        Mockito.when(mockMatcher.getMatchingOrders(orderDb, newOrder)).thenReturn(orderDb);
        Mockito.when(mockTrader.makeTrades(orderDb, newOrder)).thenReturn(trades);
        ServiceManager service = new ServiceManager(mockMatcher, mockTrader);
        service.handleNewOrder(newOrder, orderDb, tradeDb);
        assertTrue(orderDb.contains(newOrder));
    }

    @Test
    void Should_RemoveCompletedOrdersFromDb_WhenPassedNewOrderWithMatches() {
        Order newOrder = (new Order(
                new UUID(7,7),40f, 40f, Exchange.BUY, Market.CAD));
        Order testDbOrder0 = new Order(
                new UUID(4,4),25f, 10f, Exchange.SELL, Market.CAD);
        Order testDbOrder1 = new Order(
                new UUID(5,5),30f, 10f, Exchange.SELL, Market.CAD);
        Order testDbOrder2 = new Order(
                new UUID(6,6),35f, 10f, Exchange.SELL, Market.CAD);

        List<Order> orderDb = new ArrayList<Order>() {{
            add(testDbOrder0);
            add(testDbOrder1);
            add(testDbOrder2);
        }};

        List<Trade> trades = new ArrayList<Trade>() {{
            add(new Trade(testDbOrder0, newOrder));
            add(new Trade(testDbOrder1, newOrder));
            add(new Trade(testDbOrder2, newOrder));
        }};

        List<Trade> tradeDb = new ArrayList<>();

        Mockito.when(mockMatcher.getMatchingOrders(orderDb, newOrder)).thenReturn(orderDb);
        Mockito.when(mockTrader.makeTrades(orderDb, newOrder)).thenReturn(trades);
        ServiceManager service = new ServiceManager(mockMatcher, mockTrader);
        service.handleNewOrder(newOrder, orderDb, tradeDb);
        assertFalse(orderDb.contains(testDbOrder0));
        assertFalse(orderDb.contains(testDbOrder1));
        assertFalse(orderDb.contains(testDbOrder2));
    }
}
