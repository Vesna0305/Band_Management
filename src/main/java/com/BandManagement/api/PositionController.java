package com.BandManagement.api;

import com.BandManagement.persistence.model.Position;
import com.BandManagement.persistence.repositories.MemberRepository;
import com.BandManagement.persistence.repositories.PositionRepository;
import com.BandManagement.service.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
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
    public List<Position> getPositions(Model model) {
        return positionService.getPositions();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePosition(@RequestBody Position position, @PathVariable UUID positionId) {
        positionService.getPositionById(positionId);
    }

    @PostMapping
    public ResponseEntity<?> savePosition(@RequestBody Position position, Model model) {
        model.addAttribute("positions", positionRepository.findAll());
        return new ResponseEntity<>(positionRepository.save(position), HttpStatus.CREATED);
    }

    /*@GetMapping("/position/{id}")
    public String aboutPage(@PathVariable("id") UUID id, Model model) {
       Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("position", position);
        return "create-member";
    }*/

}
