package ita.tw.afs.spark.mapper;

import ita.tw.afs.spark.dto.OrdersResponse;
import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.model.ParkingLot;

import java.util.HashMap;

public class OrdersMapper {
    public OrdersResponse mappedResponse(HashMap<String, Object> keyValue) {
        Orders order = (Orders) keyValue.get("Order");
        ParkingLot parkingLot = (ParkingLot) keyValue.get("ParkingLot");
        Double cost = (Double) keyValue.get("Cost");

        OrdersResponse ordersResponse = new OrdersResponse();
        ordersResponse.setOrderNumber(order.getOrderId());
        ordersResponse.setKey(order.getOrderId());
        ordersResponse.setParkingLotName(parkingLot.getName());
        ordersResponse.setParkingLotId(parkingLot.getId());
        ordersResponse.setParkingBlockPosition(order.getParkingBlockPosition());
        ordersResponse.setPlateNumber(order.getPlateNumber());
        ordersResponse.setPrice(String.valueOf(parkingLot.getRate()));
        ordersResponse.setCost(cost);
        ordersResponse.setTimeIn(order.getTimeIn());
        return ordersResponse;
    }
}
