package com.inv.portfolio.service;

import com.inv.portfolio.model.Position;
import com.inv.portfolio.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final PositionRepository positionRepository;

    public PortfolioService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
}
