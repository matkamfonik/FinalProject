package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.entities.TaxMonth;

import java.util.List;
import java.util.Optional;

public interface CostPositionService {

    List<CostPosition> getList();

    Optional<CostPosition> get(Long id);

    void save(CostPosition costPosition);

    List<TaxMonth> findByCostPositionIdOrderByIdDesc(Long taxMonthId);


}
