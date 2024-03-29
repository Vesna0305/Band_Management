package com.BandManagement.persistence.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(length = 20, nullable = false)
    String genreName;

    @OneToMany(mappedBy="genre", fetch = FetchType.EAGER)
    private List<Band> band;

}
