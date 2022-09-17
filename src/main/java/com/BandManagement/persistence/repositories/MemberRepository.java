package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.Member;
import com.BandManagement.persistence.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Component
@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findAll();

    public List<Member> getMemberByBandId(UUID band_id);

}
