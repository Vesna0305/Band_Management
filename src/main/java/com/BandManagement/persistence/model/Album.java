package com.BandManagement.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(length = 30, nullable = false)
    String albumName;

    @Column(length = 4, nullable = false)
    long year;

    @Column(length = 100, nullable = true)
    String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "band_id")
    private Band band;

}
