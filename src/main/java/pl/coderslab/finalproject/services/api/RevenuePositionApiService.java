package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.RevenuePosition;
import pl.coderslab.finalproject.mappers.RevenuePositionMapper;
import pl.coderslab.finalproject.repository.RevenuePositionRepository;
import pl.coderslab.finalproject.services.calculation.RevenueCalculationService;
import pl.coderslab.finalproject.services.interfaces.RevenuePositionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class RevenuePositionApiService implements RevenuePositionService {
    private final RevenuePositionRepository revenuePositionRepository;

    private final RevenueCalculationService revenueCalculationService;

    private final TaxYearApiService taxYearService;

    private final TaxMonthApiService taxMonthService;

    private final RevenuePositionMapper revenuePositionMapper;

    @Override
    public RevenuePositionDTO get(Long id) {
        return revenuePositionMapper.toDto(revenuePositionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void save(RevenuePositionDTO revenuePositionDTO, Long taxMonthId, Long businessId, Long taxYearId) {
        RevenuePosition revenuePosition = revenueCalculationService.calculate(revenuePositionDTO, taxMonthId);
        revenuePositionRepository.save(revenuePosition);
        taxMonthService.setNextMonthsNotUpToDate(taxMonthId, businessId, taxYearId);
    }

    @Override
    public List<RevenuePositionDTO> findAllRevenuePositions(Long taxMonthId) {
        return revenuePositionRepository.findAllByTaxMonthId(taxMonthId).stream().map(revenuePositionMapper::toDto).collect(Collectors.toList());
    }


}
