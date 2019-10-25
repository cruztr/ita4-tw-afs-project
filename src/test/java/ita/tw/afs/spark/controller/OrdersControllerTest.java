package ita.tw.afs.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ita.tw.afs.spark.model.Orders;
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
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrdersController.class)
@ActiveProfiles(profiles = "test")
class OrdersControllerTest {

    @MockBean
    OrdersService ordersService;

    @Autowired
    MockMvc mvc;

    public Orders createDummyOrder() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        Orders orders = new Orders();
        orders.setPlateNumber("DXT-312");
        orders.setTimeIn(myDateObj.format(myFormatObj));
        orders.setTimeOut(null);
        orders.setCreatedBy(1L);
        orders.setClosedBy(null);
        orders.setParkingLot(new ArrayList<>());
        return orders;
    }


    @Test
    void should_add_orders() throws Exception {
        when(ordersService.save(any(), anyLong())).thenReturn(createDummyOrder());

        ResultActions result = mvc.perform(post("/orders/parkingLot/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDummyOrder())));

        result.andExpect(status().isCreated());
    }

}