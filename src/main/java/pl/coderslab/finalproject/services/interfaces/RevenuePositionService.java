package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.RevenuePosition;

import java.util.List;
import java.util.Optional;

public interface RevenuePositionService {

    Optional<RevenuePosition> get(Long id);

    void add(RevenuePosition revenuePosition);

    void delete(Long id);

    List<RevenuePosition> findRevenuePositions(Long taxMonthId);
}
