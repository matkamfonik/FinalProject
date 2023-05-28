package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.entities.RevenuePosition;

import java.util.List;
import java.util.Optional;

public interface RevenuePositionService {

    List<RevenuePosition> getList();

    Optional<RevenuePosition> get(Long id);

    void save(RevenuePosition revenuePosition);

    public List<RevenuePosition> findAllByTaxMonthId(Long taxMonthId);
}
