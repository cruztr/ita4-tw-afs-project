package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.service.OrdersService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping(value = "/parkingLot/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(@RequestBody Orders orders, @PathVariable Long id) throws NotFoundException {
        return ordersService.save(orders, id);
    }

}
