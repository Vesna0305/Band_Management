package com.BandManagement.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy="band")
    private List<Member> member;

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/band-photos/" + id + "/" + photo;
    }

    public void replaceAll() {
        getBandDescription().replaceAll("\n\r", "<br>");
    }

}
