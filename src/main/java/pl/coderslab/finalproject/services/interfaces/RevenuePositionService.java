package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.TaxMonth;

import java.util.List;

public interface RevenuePositionService {

    RevenuePositionDTO get(Long id);

    void add(RevenuePositionDTO revenuePositionDTO, TaxMonth taxMonth);

    List<RevenuePositionDTO> findAllRevenuePositions(Long taxMonthId);
}
