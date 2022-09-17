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
@Table(name = "band")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(length = 30, nullable = false)
    String bandName;

    @Column()
    private String photo;

    @Column(length = 4, nullable = false)
    long year;

    @Column(columnDefinition = "TEXT")
    private String bandDescription;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @JsonIgnore
    @OneToMany(mappedBy="band", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Member> member;
    @JsonIgnore
    @OneToMany(mappedBy="band", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Album> albums;
    @JsonIgnore
    @OneToMany(mappedBy="band", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<News> news;

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/band-photos/" + id + "/" + photo;
    }

}
