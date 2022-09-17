package com.BandManagement.service;

import com.BandManagement.persistence.model.Orders;
import com.BandManagement.persistence.model.OrdersProduct;
import com.BandManagement.persistence.model.Product;
import com.BandManagement.persistence.model.Track;
import com.BandManagement.persistence.repositories.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public Orders createOrder(Orders order) {
        return ordersRepository.save(order);
    }

    public List<Orders> getOrders() {
        return ordersRepository.findAll();
    }

    public void deleteOrdersById(UUID id) {
        ordersRepository.deleteById(id);
    }

    public Orders getOrdersById(UUID id) {
        return ordersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
    }

    public List<Orders> findByLastNameContainingIgnoreCase(@Pattern(regexp = "[A-Za-z]+") String keyword) {
        var orders = ordersRepository.findAll();
        var ordersList = new ArrayList<Orders>();

        orders.forEach(o -> {
            if(o.getUser().getLastName().toLowerCase().contains(keyword.toLowerCase())) {
                ordersList.add(o);
            }
        });
        return ordersList;
    }


}
