package com.BandManagement.service;

import com.BandManagement.persistence.model.Member;
import com.BandManagement.persistence.model.Position;
import com.BandManagement.persistence.repositories.MemberRepository;
import com.BandManagement.persistence.repositories.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final MemberRepository memberRepository;

    public Position createPosition(Position position) {
        return positionRepository.save(position);
    }

    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    public void deletePositionsById(UUID id) {
        positionRepository.deleteById(id);
    }

    public Position getPositionById(UUID id) {
        return positionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
    }
}
