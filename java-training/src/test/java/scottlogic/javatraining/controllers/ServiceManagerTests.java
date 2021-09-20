package scottlogic.javatraining.controllers;

import org.junit.jupiter.api.Test;
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

    @Test
    void Should_AddTradesToTradeDb_WhenPassedNewOrderWithMatches() {

        Order newOrder = (new Order(
                new UUID(7,7),40f, 30f, Exchange.BUY, Market.CAD));

        List<Order> orderDb = new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),30f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(4,4),25f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),30f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),35f, 10f, Exchange.SELL, Market.CAD));
        }};

        List<Trade> tradeDb = new ArrayList<>();

        ServiceManager.handleNewOrder(newOrder, orderDb, tradeDb);
        assertEquals(3, tradeDb.size());
    }

    @Test
    void Should_AddInCompleteOrderToOrderDb_IfOrderHasNoMatches() {
        Order newOrder = (new Order(
                new UUID(7,7),40f, 40f, Exchange.BUY, Market.CAD));
        List<Order> orderDb = new ArrayList<>();
        List<Trade> tradeDb = new ArrayList<>();

        ServiceManager.handleNewOrder(newOrder, orderDb, tradeDb);
        assertTrue(orderDb.contains(newOrder));
    }

    @Test
    void Should_AddInCompleteOrderToOrderDb_IfOrderHasMatchesButRemainedInComplete() {
        Order newOrder = (new Order(
                new UUID(7,7),40f, 40f, Exchange.BUY, Market.CAD));

        List<Order> orderDb = new ArrayList<Order>() {{
            add(new Order(new UUID(4,4),25f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),30f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),35f, 10f, Exchange.SELL, Market.CAD));
        }};

        List<Trade> tradeDb = new ArrayList<>();

        ServiceManager.handleNewOrder(newOrder, orderDb, tradeDb);
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

        List<Trade> tradeDb = new ArrayList<>();

        ServiceManager.handleNewOrder(newOrder, orderDb, tradeDb);
        assertFalse(orderDb.contains(testDbOrder0));
        assertFalse(orderDb.contains(testDbOrder1));
        assertFalse(orderDb.contains(testDbOrder2));
    }
}
