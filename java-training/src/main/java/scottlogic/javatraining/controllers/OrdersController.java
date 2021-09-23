package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scottlogic.javatraining.interfaces.IOrderService;
import scottlogic.javatraining.models.Order;
import scottlogic.javatraining.models.OrderRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    private IOrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getDbOrders();
        return orders == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(orders);
    }

    @GetMapping (path="/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable final UUID id) {
        Order requestedOrder = orderService.getOrder(id);

        return requestedOrder == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(requestedOrder);
    }

    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<Order> postOrder(@RequestBody OrderRequest request) {
        Order newOrder = orderService.postOrder(request);
        return ResponseEntity.ok().body(newOrder);
    }
}
