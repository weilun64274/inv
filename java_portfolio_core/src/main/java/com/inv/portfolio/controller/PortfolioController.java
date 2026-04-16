package com.inv.portfolio.controller;

import com.inv.portfolio.model.Position;
import com.inv.portfolio.repository.PositionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    
    private final PositionRepository positionRepository;

    public PortfolioController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping("/positions")
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }
}
