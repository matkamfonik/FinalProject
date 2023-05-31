package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.TaxMonth;

import java.util.List;

public interface CostPositionService {

    CostPositionDTO getDTO(Long id);

    void delete(Long id);

    void add(CostPositionDTO costPositionDTO, TaxMonth taxMonth, Long businessId, Long taxYearId);

    List<CostPositionDTO> findAllCostPositions(Long taxMonthId);
}
