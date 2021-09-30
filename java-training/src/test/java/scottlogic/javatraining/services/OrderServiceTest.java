package scottlogic.javatraining.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import scottlogic.javatraining.models.*;
import scottlogic.javatraining.repositories.OrderRepository;
import scottlogic.javatraining.repositories.TradeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    OrderRepository mockOrderRepository;
    TradeRepository mockTradeRepository;
    Matcher mockMatcher;
    Trader mockTrader;
    OrderService orderService;

    @BeforeEach
    void setUp() {
        mockOrderRepository = Mockito.mock(OrderRepository.class);
        mockTradeRepository = Mockito.mock(TradeRepository.class);
        mockMatcher = Mockito.mock(Matcher.class);
        mockTrader = Mockito.mock(Trader.class);
        orderService = new OrderService(mockMatcher, mockTrader, mockOrderRepository, mockTradeRepository);
    }

    @Test
    void Should_ReturnAllOrdersFromDb() {

        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),30f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(4,4),10f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),30f, 10f, Exchange.SELL, Market.CAD));
        }};

        when(mockOrderRepository.findAll()).thenReturn(dbOrders);
        assertEquals(orderService.getDbOrders(), dbOrders);
    }

    @Test
    void Should_ReturnAnOrderFromRepository() {
        UUID testId = new UUID(1, 1);
        Order testOrder = new Order(testId, 10f, 10f, Exchange.BUY, Market.EUR);
        when(mockOrderRepository.findById(testId)).thenReturn(Optional.of(testOrder));
        assertEquals(orderService.getOrder(testId), testOrder);
        verify(mockOrderRepository, times(1)).findById(testId);
    }

    @Test
    void Should_CallDeleteOnRepository() {
        UUID testId = new UUID(1, 1);
        orderService.deleteOrder(testId);
        verify(mockOrderRepository, times(1)).deleteById(testId);
    }

    @Test
    void Should_CallAllDbMethods_WhenPassedOrderRequestWhichHasMatchAndIsCompleteAfter() {
        OrderRequest testOrderReq = new OrderRequest(
                new UUID(1, 1), 10f, 10f, Exchange.BUY, Market.EUR);
        Order testOrder = new Order(
                testOrderReq.userId, testOrderReq.price, testOrderReq.quantity, testOrderReq.exchange, testOrderReq.market);
        List<Order> matchedOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(4,4),10f, 10f, Exchange.SELL, Market.EUR));
        }};

        List<Trade> trades = new ArrayList<Trade>() {{
            add(new Trade(matchedOrders.get(0), testOrder));
        }};

        when(mockOrderRepository.findAll()).thenReturn(matchedOrders);
        when(mockMatcher.getMatchingOrders(anyList(), any(Order.class))).thenReturn(matchedOrders);
        when(mockTrader.makeTrades(anyList(), any(Order.class))).thenReturn(trades);
        orderService.postOrder(testOrderReq);
        verify(mockMatcher, times(1)).getMatchingOrders(anyList(), any(Order.class));
        verify(mockTradeRepository, times(1)).saveAll(anyList());
        verify(mockOrderRepository, times(1)).deleteAll(anyList());
    }
}