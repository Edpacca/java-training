package scottlogic.javatraining.services;

import org.junit.jupiter.api.Test;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatcherTests {

    @Test
    void Should_ReturnAListOfSellOrders_WhenPassedANewBuyOrder() {
        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),
                    10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),
                    20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),
                    30f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(4,4),
                    10f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),
                    20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),
                    30f, 10f, Exchange.SELL, Market.CAD));
        }};

        Order newOrder = (new Order(new UUID(7,7),
                40f, 10f, Exchange.BUY, Market.CAD));

        List<Order> matchedOrders = Matcher.getMatchingOrders(dbOrders, newOrder);

        assertTrue(matchedOrders.stream()
                .allMatch(order -> order.getExchange() == Exchange.SELL));
    }

    @Test
    void Should_ReturnAListOfBuyOrders_WhenPassedANewSellOrder() {
        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),
                    10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),
                    20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),
                    30f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(4,4),
                    10f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),
                    20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),
                    30f, 10f, Exchange.SELL, Market.CAD));
        }};

        Order newOrder = (new Order(new UUID(7,7),
                40f, 10f, Exchange.SELL, Market.CAD));

        List<Order> matchedOrders = Matcher.getMatchingOrders(dbOrders, newOrder);

        assertTrue(matchedOrders.stream()
                .allMatch(order -> order.getExchange() == Exchange.BUY));
    }

    @Test
    void Should_ReturnAListOfMatchingMarket_WhenPassedANewOrder() {
        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),
                    10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),
                    20f, 10f, Exchange.BUY, Market.GBP));
            add(new Order(new UUID(3,3),
                    30f, 10f, Exchange.BUY, Market.JPY));
            add(new Order(new UUID(4,4),
                    10f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),
                    20f, 10f, Exchange.SELL, Market.USD));
            add(new Order(new UUID(6,6),
                    30f, 10f, Exchange.SELL, Market.CAD));
        }};

        Market testMarket = Market.CAD;
        Order newOrder = (new Order(new UUID(7,7),
                40f, 10f, Exchange.BUY, testMarket));

        List<Order> matchedOrders = Matcher.getMatchingOrders(dbOrders, newOrder);

        assertTrue(matchedOrders.stream()
                .allMatch(order -> order.getMarket() == testMarket));
    }

    @Test
    void Should_FilterOrdersWithPriceLessThanOrEqualToNewOrder_WhenPassedANewBuyOrder() {
        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),
                    10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),
                    20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),
                    30f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(4,4),
                    10f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),
                    20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),
                    30f, 10f, Exchange.SELL, Market.CAD));
        }};

        float testPrice = 20f;
        Order newOrder = (new Order(new UUID(7,7),
                testPrice, 10f, Exchange.BUY, Market.CAD));

        List<Order> matchedOrders = Matcher.getMatchingOrders(dbOrders, newOrder);

        assertTrue(matchedOrders.stream()
                .allMatch(order -> order.getPrice() <= testPrice));
    }

    @Test
    void Should_FilterOrdersWithPriceGreaterThanOrEqualToNewOrder_WhenPassedANewSellOrder() {
        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),
                    10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),
                    20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),
                    30f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(4,4),
                    10f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),
                    20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),
                    30f, 10f, Exchange.SELL, Market.CAD));
        }};

        float testPrice = 20f;
        Order newOrder = (new Order(new UUID(7,7),
                testPrice, 10f, Exchange.SELL, Market.CAD));

        List<Order> matchedOrders = Matcher.getMatchingOrders(dbOrders, newOrder);

        assertTrue(matchedOrders.stream()
                .allMatch(order -> order.getPrice() >= testPrice));
    }

    @Test
    void ShouldNot_ReturnAnyOrdersWithSameUserIdAsNewOrder_WhenPassedANewOrder() {
        UUID testId = new UUID(1, 1);

        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(testId,10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(testId, 20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),
                    20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),
                    30f, 10f, Exchange.BUY, Market.CAD));
        }};

        Order newOrder = new Order(testId,10f, 10f, Exchange.SELL, Market.CAD);
        List<Order> matchedOrders = Matcher.getMatchingOrders(dbOrders, newOrder);

        assertTrue(matchedOrders.stream()
                .noneMatch(order -> order.getUserId() == testId));
    }
}
