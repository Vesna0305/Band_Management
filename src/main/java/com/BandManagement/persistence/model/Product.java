package com.BandManagement.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(length = 50, nullable = false)
    String productName;

    @Column(columnDefinition = "TEXT", nullable = false)
    String description;

    @Column(length = 100, nullable = false)
    String image;

    @Column(length = 4, nullable = false)
    long year;

    @Column(nullable = false)
    float unitPrice;

    @Column()
    int quantity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categories;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "band_id", nullable = false)
    private Band band;

}
