package scottlogic.javatraining.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scottlogic.javatraining.delegates.OrderDelegate;
import scottlogic.javatraining.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class OrdersController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public void mapperConfig() {
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    @Autowired
    public void dbTest() {
        OrderDelegate delegate = new OrderDelegate();
    }


    private final List<Order> dbOrders = new ArrayList<Order>() {{
        add(new Order(UUID.randomUUID(),80f, 10f, Exchange.BUY, Market.CAD));
        add(new Order(UUID.randomUUID(),70f, 34f, Exchange.BUY, Market.USD));
        add(new Order(UUID.randomUUID(),66f, 52f, Exchange.BUY, Market.JPY));
        add(new Order(UUID.randomUUID(),54f, 17f, Exchange.SELL, Market.GBP));
        add(new Order(UUID.randomUUID(),102f, 14f, Exchange.SELL, Market.CHF));
        add(new Order(UUID.randomUUID(),99f, 20f, Exchange.SELL, Market.EUR));
    }};

    @GetMapping (path ="/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> dbOrderDTOs = dbOrders
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(dbOrderDTOs);
    }

    @GetMapping (path="/order/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable final UUID id) {
        Order requestedOrder = dbOrders.stream()
                .filter(order -> id.equals(order.getId()))
                .findAny()
                .orElse(null);

        return requestedOrder == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(
                        modelMapper.map(requestedOrder, OrderDTO.class));
    }

    @PostMapping(path="/order", headers = "Accept=application/json")
    public ResponseEntity<Order> postOrder(@RequestBody OrderRequest request) {
        Order order = new Order(
                request.userId,
                request.price,
                request.quantity,
                request.exchange,
                request.market
        );
        return ResponseEntity.ok().body(order);
    }
}
