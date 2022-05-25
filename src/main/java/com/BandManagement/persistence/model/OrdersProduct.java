package com.BandManagement.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders_products")
public class OrdersProduct {

    /*private static final long serialVersionUID = 1L;*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
}
