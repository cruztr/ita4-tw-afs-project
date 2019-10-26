package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.service.OrdersService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/spark")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping(value = "/parkingBoy/{parkingBoyId}/orders", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(@RequestBody Orders orders, @PathVariable Long parkingBoyId) throws NotFoundException {
        return ordersService.saveIfHasAvailableParkingBlocks(orders, parkingBoyId);
    }

    @GetMapping(value = "/parkingBoy/{parkingBoyid}/orders", produces = APPLICATION_JSON_VALUE)
    public Iterable<Orders> listOrders(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                       @PathVariable Long parkingBoyid) {
        return ordersService.getOrdersByPage(parkingBoyid);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/parkingBoy/{parkingBoyId}/orders/{orderId}", produces = APPLICATION_JSON_VALUE)
    public Optional<Orders> getOrder(@PathVariable Long parkingBoyId, @PathVariable Long orderId) throws NotFoundException {
        return ordersService.getOrderByIdAndParkingNumber(parkingBoyId,orderId);
    }



}
