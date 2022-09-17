package com.BandManagement.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column()
    String orderDate;

    @Column()
    Boolean orderStatus;

    @Column()
    private float totalOrderPrice;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    /*@JoinColumn(name = "order_id")*/
    private List<OrdersProduct> ordersProduct;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

//    @PrePersist
//    protected void onCreate() {
//        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//    }

    public void setOrderDate() {
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

}
