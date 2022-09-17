package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.News;
import com.BandManagement.persistence.model.Orders;
import com.BandManagement.persistence.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Component
@Repository
public interface OrdersRepository extends JpaRepository<Orders, UUID> {

    Orders findOrdersByUserIdAndOrderStatus(UUID id, boolean status);
    List<Orders> findAllByOrderByTotalOrderPriceDescUserIdAsc();
    List<Orders> findByUserEmailOrderByOrderStatusDesc(String email);

}
