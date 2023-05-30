package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.RevenuePosition;

import java.util.List;
import java.util.Optional;

public interface RevenuePositionService {

    RevenuePositionDTO get(Long id);

    void save(RevenuePositionDTO revenuePositionDTO, Long taxMonthId, Long businessId, Long taxYearId);

    public List<RevenuePositionDTO> findAllRevenuePositions(Long taxMonthId);
}
