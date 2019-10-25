package ita.tw.afs.spark.service;


import ita.tw.afs.spark.model.Orders;
import ita.tw.afs.spark.repository.OrdersRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;


    public Orders save(Orders orders, Long id) throws NotFoundException {
        return ordersRepository.save(orders);
    }
}
