package ita.tw.afs.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.repository.OrdersRepository;
import ita.tw.afs.spark.repository.ReservationRepository;
import ita.tw.afs.spark.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@ActiveProfiles(profiles = "OrdersControllerTest")
class OrdersControllerTest {

    @MockBean
    OrdersService ordersService;

    @MockBean
    OrdersRepository ordersRepository;

    @MockBean
    ReservationRepository reservationRepository;

    @Autowired
    MockMvc mvc;


    @Test
    void should_add_orders() throws Exception {
        when(ordersService.saveIfHasAvailableParkingBlocks(any(), anyLong())).thenReturn(createDummyOrder());

        ResultActions result = mvc.perform(post("/spark/parkingBoy/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDummyOrder())));

        result.andExpect(status().isCreated());
    }

    @Test
    void should_get_all_orders() throws Exception {
        when(ordersService.getOrdersByPage()).thenReturn(Collections.singletonList(createDummyOrder()));

        ResultActions result = mvc.perform(get("/spark/parkingBoy/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDummyOrder())));

        result.andExpect(status().isOk());
    }

    @Test
    void should_get_order_by_order_id() throws Exception {
        Long orderId = 1L;
        Optional<Orders> dummyOrderOptional = Optional.of(createDummyOrder());
        when(ordersService.getOrderById(orderId)).thenReturn(dummyOrderOptional);

        ResultActions result = mvc.perform(get("/spark/parkingBoy/orders/{orderId}",  orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dummyOrderOptional.get())));

        result.andExpect(status().isOk());
    }

    @Test
    void should_close_order_by_id() throws Exception {
        Long parkingBoyId = 111L;
        Orders order = new Orders();
        order.setOrderId(2L);
        Optional<Orders> ordersOptional = Optional.of(createDummyOrder());
        ordersOptional.get().setTimeOut(String.valueOf(LocalDateTime.now()));
        ordersOptional.get().setClosedBy(order.getOrderId());
        ordersOptional.get().setPrice(10.00);
        when(ordersService.closeOrderById(parkingBoyId, order)).thenReturn(ordersOptional);

        ResultActions result = mvc.perform(patch("/spark/parkingBoy/{parkingBoyId}/orders", parkingBoyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ordersOptional.get())));

        result.andExpect(status().isOk());
    }

    public Orders createDummyOrder() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        Orders orders = new Orders();
        orders.setPlateNumber("DXT-312");
        orders.setTimeIn(myDateObj.format(myFormatObj));
        orders.setTimeOut(null);
        orders.setCreatedBy(1L);
        orders.setClosedBy(null);
        orders.setPrice(null);
        return orders;
    }
}