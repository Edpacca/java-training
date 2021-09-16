package scottlogic.javatraining.models;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTests {

    @Test
    void Should_ThrowError_WhenPassedInvalidQuantity() {
        Order testOrder = new Order(new UUID(111,111),
                10f, 10f, Exchange.BUY, Market.CAD);
        System.out.println(testOrder.getUserId());
        assertThrows(Error.class, () -> testOrder.reduceQuantity(20f));
    }

    @Test
    void Should_ReduceQuantityByCorrectAmount_WhenPassedValidQuantity() {
        Order testOrder = new Order(UUID.randomUUID(),
                10f, 10f, Exchange.BUY, Market.CAD);

        testOrder.reduceQuantity(5f);
        assertEquals(5f, testOrder.getQuantity());
    }

    @Test
    void Should_SetIsCompletedToTrue_WhenQuantityIsReducedToZero() {
        Order testOrder = new Order(UUID.randomUUID(),
                10f, 10f, Exchange.BUY, Market.CAD);
        assertFalse(testOrder.isComplete());
        testOrder.reduceQuantity(10f);
        assertTrue(testOrder.isComplete());
    }
}
