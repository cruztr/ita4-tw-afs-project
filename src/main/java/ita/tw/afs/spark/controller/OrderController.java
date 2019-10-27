package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.service.OrdersService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/spark/parkingBoy")
public class OrderController {

    @Autowired
    OrdersService ordersService;

    @PostMapping(value = "/{parkingBoyId}/orders", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(@RequestBody Orders orders, @PathVariable Long parkingBoyId) throws NotFoundException, NotSupportedException {
        return ordersService.saveIfHasAvailableParkingBlocks(orders, parkingBoyId);
    }

    @GetMapping(value = "/orders", produces = APPLICATION_JSON_VALUE)
    public Iterable<Orders> listOrders(@RequestParam(required = false, defaultValue = "0") Integer page,
                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ordersService.getOrdersByPage();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/orders/{orderId}", produces = APPLICATION_JSON_VALUE)
    public Optional<Orders> getOrder(@PathVariable Long orderId) throws NotFoundException {
        return ordersService.getOrderById(orderId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{parkingBoyId}/orders/{orderId}", produces = APPLICATION_JSON_VALUE)
    public Optional<Orders> closeOrder(@PathVariable Long parkingBoyId, @PathVariable Long orderId) throws NotFoundException {
        return ordersService.closeOrderById(parkingBoyId, orderId);
    }
}
