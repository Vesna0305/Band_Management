package com.BandManagement.api;

import com.BandManagement.persistence.model.Position;
import com.BandManagement.persistence.repositories.MemberRepository;
import com.BandManagement.persistence.repositories.PositionRepository;
import com.BandManagement.service.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionRepository positionRepository;
    private final PositionService positionService;
    private final MemberRepository memberRepository;

    @PostMapping("/create")
    public void createPosition(@RequestBody Position position) {
        positionService.createPosition(position);
    }

    @GetMapping("/get-all-positions")
    public List<Position> getPositions() {
        return positionService.getPositions();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePosition(@RequestBody Position position, @PathVariable UUID positionId) {
        positionService.getPositionById(positionId);
    }
}
