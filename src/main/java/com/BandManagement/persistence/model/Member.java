package com.BandManagement.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(length = 30, nullable = false)
    String firstName;

    @Column(length = 30, nullable = false)
    String lastName;

    @Column()
    private String photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "band_id")
    private Band band;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "member_position",
            joinColumns = @JoinColumn(
                    name = "member_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "position_id", referencedColumnName = "id"))

    private Collection<Position> positions;

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/member-photos/" + id + "/" + photo;
    }

}
