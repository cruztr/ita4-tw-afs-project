package ita.tw.afs.spark.service;


import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.repository.OrdersRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class OrdersService {
    private static final String OBJECT_NOT_FOUND = "OBJECT_NOT_FOUND";

    @Autowired
    OrdersRepository ordersRepository;


    public Orders save(Orders orders, Long parkingBoyid) throws NotFoundException {
        orders.setTimeIn(getCurrentDateTime());
        orders.setCreatedBy(parkingBoyid);
        return ordersRepository.save(orders);
    }

    public Iterable<Orders> getOrdersByPage(Long createdBy) {
        return ordersRepository.findAllByCreatedBy(createdBy);
    }

    public Optional<Orders> getOrderByIdAndParkingNumber(Long createdBy,Long orderId) throws NotFoundException {
        Optional<Orders> checkIfOrderExists = ordersRepository.findByOrderIdAndCreatedBy(orderId,createdBy);

        if (checkIfOrderExists.isPresent()) {
            return checkIfOrderExists;
        }

        throw new NotFoundException(OBJECT_NOT_FOUND);
    }

    private String getCurrentDateTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }
}
