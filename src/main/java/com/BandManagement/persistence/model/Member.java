package com.BandManagement.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

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
    String dateOfBirth;

    @Column(columnDefinition = "TEXT")
    private String memberDescription;

    @Column()
    private String photo;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id")
    private Band band;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_position",
            joinColumns = @JoinColumn(
                    name = "member_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "position_id", referencedColumnName = "id"))
    private Set<Position> positions = new HashSet<>();

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/member-photos/" + id + "/" + photo;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }


}
