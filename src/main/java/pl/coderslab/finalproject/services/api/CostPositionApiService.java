package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.mappers.CostPositionMapper;
import pl.coderslab.finalproject.repository.CostPositionRepository;
import pl.coderslab.finalproject.services.calculation.CostCalculationService;
import pl.coderslab.finalproject.services.interfaces.CostPositionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CostPositionApiService implements CostPositionService {
    private final CostPositionRepository costPositionRepository;

    private final CostCalculationService costCalculationService;

    private final TaxYearApiService taxYearService;

    private final TaxMonthApiService taxMonthService;

    private final CostPositionMapper costPositionMapper;

    @Override
    public CostPositionDTO get(Long id) {
        return costPositionMapper.toDto(costPositionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void save(CostPositionDTO costPositionDTO, Long taxMonthId, Long businessId, Long taxYearId) {
        CostPosition costPosition = costCalculationService.calculate(costPositionDTO, taxMonthId, businessId);
        costPositionRepository.save(costPosition);
        taxMonthService.setNextMonthsNotUpToDate(taxMonthId, businessId, taxYearId);
    }

    @Override
    public List<CostPositionDTO> findAllCostPositions(Long taxMonthId) {
        return costPositionRepository.findAllByTaxMonthId(taxMonthId).stream().map(costPositionMapper::toDto).collect(Collectors.toList());
    }

}
