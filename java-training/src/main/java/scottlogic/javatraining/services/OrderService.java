package scottlogic.javatraining.services;

import scottlogic.javatraining.interfaces.IOrderService;
import scottlogic.javatraining.interfaces.IMatcher;
import scottlogic.javatraining.interfaces.ITrader;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.OrderRequest;
import scottlogic.javatraining.models.Trade;
import scottlogic.javatraining.repositories.OrderRepository;
import scottlogic.javatraining.repositories.TradeRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {

    private final IMatcher matcher;
    private final ITrader trader;

    OrderRepository orderRepository;
    TradeRepository tradeRepository;

    public OrderService(IMatcher matcher, ITrader trader,
                        OrderRepository orderRepository, TradeRepository tradeRepository) {
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
        this.matcher = matcher;
        this.trader = trader;
    }

    public List<Order> getDbOrders() {
        return orderRepository.findAll();
    }
    public Order getOrder(UUID id) { return orderRepository.findById(id).orElse(null); }
    public void deleteOrder(UUID id) { orderRepository.deleteById(id); }

    public Order postOrder(OrderRequest orderRequest) {
        Order newOrder = new Order(
                orderRequest.userId,
                orderRequest.price,
                orderRequest.quantity,
                orderRequest.exchange,
                orderRequest.market
        );
        handleNewOrder(newOrder);
        return newOrder;
    }

    private void handleNewOrder(Order order) {
        List<Order> matchedOrders = matcher.getMatchingOrders(orderRepository.findAll(), order);

        if (matchedOrders.size() > 0) {
            List<Trade> newTrades = trader.makeTrades(matchedOrders, order);
            tradeRepository.saveAll(newTrades);
        }
        if (!order.isComplete()) orderRepository.save(order);

        removeCompletedOrders(matchedOrders);
    }

    private void removeCompletedOrders(List<Order> matchedOrders) {
        List<Order> completed = matchedOrders.stream()
                .filter(Order::isComplete)
                .collect(Collectors.toList());

        orderRepository.deleteAll(completed);
    }
}
