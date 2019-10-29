package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.dto.GeneralResponse;
import ita.tw.afs.spark.dto.OrdersResponse;
import ita.tw.afs.spark.dto.TypeValuePair;
import ita.tw.afs.spark.exception.GeneralException;
import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.service.LogsService;
import ita.tw.afs.spark.service.OrdersService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/spark/parkingBoy")
public class OrderController {

    public static final String PARKING_BOY = "Parking Boy";
    public static final String ORDER = "Order";
    public static final String CREATE_ORDER = "Create Order ";
    public static final String CLOSE_ORDER = "Close Order ";
    @Autowired
    OrdersService ordersService;

    @Autowired
    private LogsService logsService;

    @PostMapping(value = "/{parkingBoyId}/orders", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(@RequestBody Orders orders, @PathVariable Long parkingBoyId) throws NotFoundException, NotSupportedException {
        Orders order = ordersService.saveIfHasAvailableParkingBlocks(orders, parkingBoyId);
        logsService.createLogs(parkingBoyId,
                PARKING_BOY, ORDER,
                CREATE_ORDER + orders.getOrderId(),
                orders.getTimeIn());
        return order;
    }

    @GetMapping(value = "/orders", produces = APPLICATION_JSON_VALUE)
    public List<OrdersResponse> listOrders(){
        return ordersService.getOrders();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/orders/{orderId}", produces = APPLICATION_JSON_VALUE)
    public Optional<Orders> getOrder(@PathVariable Long orderId) throws NotFoundException {
        return ordersService.getOrderById(orderId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{parkingBoyId}/orders", produces = APPLICATION_JSON_VALUE)
    public GeneralResponse closeOrder(@PathVariable Long parkingBoyId, @RequestBody Orders orders) throws NotFoundException, GeneralException {
        logsService.createLogs(parkingBoyId,
                PARKING_BOY, ORDER,
                CLOSE_ORDER + orders.getOrderId(),
                orders.getTimeIn());
        return ordersService.closeOrderById(parkingBoyId, orders);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/order",produces = APPLICATION_JSON_VALUE)
    public Optional<Orders> getOrderByParkingLotIdAndParkingBlockPosition(@RequestBody Orders orders) throws NotFoundException {
        return ordersService.getOrderByParkingLotIdAndParkingBlockPositionAndStatus(orders);
    }

}
