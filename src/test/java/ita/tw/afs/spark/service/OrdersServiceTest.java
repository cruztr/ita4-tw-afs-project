package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrdersService.class)
@ActiveProfiles(profiles = "test")
class OrdersServiceTest {

    @Autowired
    OrdersService ordersService;

    @MockBean
    OrdersRepository ordersRepository;

    private Orders orders;

    @BeforeEach
    void setUp() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        orders = new Orders();
        orders.setPlateNumber("DXT-312");
        orders.setTimeIn(myDateObj.format(myFormatObj));
        orders.setTimeOut(null);
        orders.setCreatedBy(1L);
        orders.setClosedBy(null);
    }

    @Test
    void should_add_orders() throws Exception {
        when(ordersRepository.save(orders)).thenReturn(orders);
        Orders isSavedOrder = ordersService.save(orders, anyLong());
        assertEquals(isSavedOrder, orders);
    }

}