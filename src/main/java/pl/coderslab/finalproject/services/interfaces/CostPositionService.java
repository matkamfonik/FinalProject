package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.entities.TaxMonth;

import java.util.List;
import java.util.Optional;

public interface CostPositionService {

    CostPositionDTO get(Long id);

    void save(CostPositionDTO costPositionDTO, Long taxMonthId, Long businessId, Long taxYearId);

    public List<CostPositionDTO> findAllCostPositions(Long taxMonthId);
}
