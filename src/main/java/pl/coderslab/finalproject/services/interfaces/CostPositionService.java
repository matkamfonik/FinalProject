package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.CostPosition;

import java.util.List;
import java.util.Optional;

public interface CostPositionService {

    Optional<CostPosition> get(Long id);

    void delete(Long id);

    void add(CostPosition costPosition);

    List<CostPosition> findCostPositions(Long taxMonthId);
}
