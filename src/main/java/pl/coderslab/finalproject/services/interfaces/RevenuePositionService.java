package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.TaxMonth;

import java.util.List;

public interface RevenuePositionService {

    RevenuePositionDTO getDTO(Long id);

    void add(RevenuePositionDTO revenuePositionDTO, TaxMonth taxMonth);

    void delete(Long id);

    List<RevenuePositionDTO> findAllRevenuePositions(Long taxMonthId);
}
