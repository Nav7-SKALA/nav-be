package com.skala.nav7.api.direction.service;


import com.skala.nav7.api.direction.dto.request.DirectionRequestDTO;
import com.skala.nav7.api.direction.entity.Direction;
import com.skala.nav7.api.direction.error.DirectionErrorCode;
import com.skala.nav7.api.direction.error.DirectionException;
import com.skala.nav7.api.direction.repository.DirectionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectionService {
    private final DirectionRepository directionRepository;

    public Direction createNewDirection(DirectionRequestDTO.CreateDTO dto) {
        return directionRepository.save(Direction.builder().prompt(dto.prompt()).build());
    }

    public Direction findRecentOneDirection() {
        return directionRepository.findFirstByOrderByCreatedAtDesc().orElseThrow(
                () -> new DirectionException(DirectionErrorCode.DIRECTION_NOT_FOUND)
        );
    }

    public List<Direction> findAll() {
        return directionRepository.findAll();
    }
}
