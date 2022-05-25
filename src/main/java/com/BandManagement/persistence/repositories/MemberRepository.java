package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Component
@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findAll();

    /*@Query( "SELECT mem FROM Band bd join bd.members mem WHERE bd.bandId = :bandId")
    List<Member> findMembersByBandId(@Param("bandId") String bandId);*/
}
