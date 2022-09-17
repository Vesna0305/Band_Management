package com.BandManagement.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
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

    @Column(columnDefinition = "TEXT")
    String description;

    @Column()
    private String photo;

    @Column(nullable = false)
    float unitPrice;

    @Column()
    int quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Categories categories;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "band_id")
    private Band band;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    /*@JoinColumn(name = "product_id")*/
    private List<OrdersProduct> ordersProduct;


    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/product-photos/" + id + "/" + photo;
    }

}
