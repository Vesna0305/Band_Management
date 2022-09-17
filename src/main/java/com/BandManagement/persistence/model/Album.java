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

    @Column()
    private String photo;

    @Column(columnDefinition = "TEXT")
    private String albumDescription;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "band_id")
    private Band band;

    @JsonIgnore
    @OneToMany(mappedBy="album", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Track> tracks;

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/album-photos/" + id + "/" + photo;
    }

}
