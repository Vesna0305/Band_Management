package com.BandManagement.service;

import com.BandManagement.persistence.model.Member;
import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.persistence.repositories.MemberRepository;
import com.BandManagement.persistence.repositories.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BandRepository bandRepository;
    private final PositionRepository positionRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public void deleteMembersById(UUID id) {
        memberRepository.deleteById(id);
    }

    public void assignBandToMember(UUID memberId, UUID bandId) {
        var member = memberRepository.findById(memberId).orElse(null);
        var band = bandRepository.findById(bandId).orElse(null);

        member.setBand(band);
        memberRepository.save(member);
    }

    public Member getMemberById(UUID id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
    }

    public void assignPositionToMember(UUID memberId, UUID positionId) {
        var member = memberRepository.getById(memberId);
        var position = positionRepository.getById(positionId);

        member.getPositions().addAll(Arrays.asList(position));

        memberRepository.save(member);
    }

}
