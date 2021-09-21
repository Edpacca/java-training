package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scottlogic.javatraining.delegates.IOrderDelegate;
import scottlogic.javatraining.models.*;

import java.util.List;
import java.util.UUID;

@RestController
public class OrdersController {

    @Autowired
    private IOrderDelegate orderDelegate;

    @GetMapping (path ="/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderDelegate.getDbOrders();
        return orders == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(orders);
    }

    @GetMapping (path="/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable final UUID id) {
        Order requestedOrder = orderDelegate.getOrder(id);

        return requestedOrder == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(requestedOrder);
    }

    @PostMapping(path="/order", headers = "Accept=application/json")
    public ResponseEntity<Order> postOrder(@RequestBody OrderRequest request) {
        Order newOrder = orderDelegate.postOrder(request);
        return ResponseEntity.ok().body(newOrder);
    }
}
