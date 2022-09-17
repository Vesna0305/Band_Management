package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.OrdersProduct;
import com.BandManagement.persistence.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Component
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> getProductByBandId(UUID band_id);
    Page<Product> findAll(Pageable pageable);
    List<Product> findAll(Sort sort);

}
