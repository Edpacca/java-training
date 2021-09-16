package scottlogic.javatraining.models;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TradeTests {

    @Test
    void Should_AssignOrderIdsToCorrectProperties_WhenInstantiated() {
        Order order1 = new Order(new UUID(111,111),
                10f, 10f, Exchange.BUY, Market.CAD);
        Order order2 = new Order(new UUID(444,444),
                20f, 12f, Exchange.SELL, Market.CAD);

        Trade trade1 = new Trade(order1, order2);
        assertEquals(order1.getId(), trade1.getBuyOrderId());
        assertEquals(order1.getUserId(), trade1.getBuyUserId());
        assertEquals(order2.getId(), trade1.getSellOrderId());
        assertEquals(order2.getUserId(), trade1.getSellUserId());
    }

    @Test
    void Should_AssignOrderIdsToCorrectProperties_WhenInstantiatedWithOrdersInEitherPosition() {
        Order order1 = new Order(new UUID(111,111),
                10f, 10f, Exchange.BUY, Market.CAD);
        Order order2 = new Order(new UUID(444,444),
                20f, 12f, Exchange.SELL, Market.CAD);
        Trade trade1 = new Trade(order1, order2);
        Trade trade2 = new Trade(order2, order1);
        assertEquals(trade1.getBuyOrderId(), trade2.getBuyOrderId());
        assertEquals(trade1.getBuyUserId(), trade2.getBuyUserId());
        assertEquals(trade1.getSellOrderId(), trade2.getSellOrderId());
        assertEquals(trade1.getSellUserId(), trade2.getSellUserId());
    }

    @Test
    void Should_ThrowAnError_IfInstantiatedWithTwoOrdersWithSameUserId() {
        UUID userId = new UUID(111,111);
        Order order1 = new Order(userId,10f, 10f, Exchange.BUY, Market.CAD);
        Order order2 = new Order(userId,20f, 12f, Exchange.SELL, Market.CAD);
        assertThrows(Error.class, () -> new Trade(order1, order2));
    }

    @Test
    void Should_ThrowAnError_IfInstantiatedWithTwoOfTheSameOrder() {
        Order order1 = new Order(new UUID(111,111),
                10f, 10f, Exchange.BUY, Market.CAD);
        assertThrows(Error.class, () -> new Trade(order1, order1));
    }

    @Test
    void Should_ThrowAnError_IfInstantiatedWithTwoOrdersWithSameExchange() {
        Order order1 = new Order(new UUID(111,111),
                10f, 10f, Exchange.BUY, Market.CAD);
        Order order2 = new Order(new UUID(444,444),
                20f, 12f, Exchange.BUY, Market.CAD);
        assertThrows(Error.class, () -> new Trade(order1, order2));
    }

    @Test
    void Should_ThrowAnError_IfInstantiatedWithTwoOrdersWithDifferentMarket() {
        Order order1 = new Order(new UUID(111,111),
                10f, 10f, Exchange.BUY, Market.CAD);
        Order order2 = new Order(new UUID(444,444),
                20f, 12f, Exchange.SELL, Market.GBP);
        assertThrows(Error.class, () -> new Trade(order1, order2));
    }
}