package scottlogic.javatraining.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import scottlogic.javatraining.models.Exchange;
import scottlogic.javatraining.models.Market;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.OrderRequest;
import scottlogic.javatraining.services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrdersControllerTests {

    OrderService mockOrderService;
    OrdersController ordersController;

    @BeforeEach
    void setUp() {
        mockOrderService = Mockito.mock(OrderService.class);
        ordersController = new OrdersController(mockOrderService);
    }

    @Test
    void Should_ReturnAllOrdersAndStatusOK_WhenDbContainsOrders() {

        List<Order> dbOrders =  new ArrayList<Order>() {{
            add(new Order(new UUID(1,1),10f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(2,2),20f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(3,3),30f, 10f, Exchange.BUY, Market.CAD));
            add(new Order(new UUID(4,4),10f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(5,5),20f, 10f, Exchange.SELL, Market.CAD));
            add(new Order(new UUID(6,6),30f, 10f, Exchange.SELL, Market.CAD));
        }};

        when(mockOrderService.getDbOrders()).thenReturn(dbOrders);
        assertEquals(ordersController.getOrders().getBody(), dbOrders);
        verify(mockOrderService, times(1)).getDbOrders();
        assertEquals(ordersController.getOrders().getStatusCode(), HttpStatus.OK);
    }

    @Test
    void Should_ReturnEmptyListAndStatusOK_WhenDbExistsButHasNoOrders() {
        List<Order> dbOrders =  new ArrayList<Order>();
        when(mockOrderService.getDbOrders()).thenReturn(new ArrayList<Order>());
        assertEquals(ordersController.getOrders().getBody(), dbOrders);
        verify(mockOrderService, times(1)).getDbOrders();
        assertEquals(ordersController.getOrders().getStatusCode(), HttpStatus.OK);
    }

    @Test
    void Should_ReturnNotFound_WhenDbReturnsNull() {
        when(mockOrderService.getDbOrders()).thenReturn(null);
        verify(mockOrderService, times(1)).getDbOrders();
        assertEquals(ordersController.getOrders().getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void Should_ReturnOrderAndStatusOK_WhenOrderExistsInDb() {
        UUID testId = new UUID(1, 1);
        Order testOrder = new Order(testId, 10f, 10f, Exchange.BUY, Market.EUR);
        when(mockOrderService.getOrder(testId)).thenReturn(testOrder);
        assertEquals(ordersController.getOrder(testId).getBody(), testOrder);
        verify(mockOrderService, times(1)).getOrder(testId);
        assertEquals(ordersController.getOrder(testId).getStatusCode(), HttpStatus.OK);
    }

    @Test
    void Should_ReturnNotFound_WhenOrderIsNotInDb() {
        UUID testId = new UUID(1, 1);
        when(mockOrderService.getOrder(testId)).thenReturn(null);
        verify(mockOrderService, times(1)).getOrder(testId);
        assertEquals(ordersController.getOrder(testId).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void Should_ReturnOrder_WhenPassedOrderRequest() {
        OrderRequest testOrderReq = new OrderRequest(new UUID(1, 1), 10f, 10f, Exchange.BUY, Market.EUR);
        Order testOrder = new Order(testOrderReq.userId, testOrderReq.price, testOrderReq.quantity, testOrderReq.exchange, testOrderReq.market);
        when(mockOrderService.postOrder(testOrderReq)).thenReturn(testOrder);
        assertEquals(ordersController.postOrder(testOrderReq).getBody(), testOrder);
        verify(mockOrderService, times(1)).postOrder(testOrderReq);
        assertEquals(ordersController.postOrder(testOrderReq).getStatusCode(), HttpStatus.OK);
    }
}
