package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.entities.CostType;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxationForm;
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

    private final CostPositionMapper costPositionMapper;

    private final CostTypeApiService costTypeService;

    private final BusinessApiService businessService;

    private final TaxationFormApiService taxationFormService;

    @Override
    public CostPositionDTO getDTO(Long id) {
        return costPositionMapper.toDto(costPositionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void delete(Long id){
        costPositionRepository.deleteById(id);
    }

    @Override
    public void add(CostPositionDTO costPositionDTO, TaxMonth taxMonth, Long businessId, Long taxYearId) {
        CostType costType = costTypeService.get(costPositionDTO.getCostTypeId()).get();
        CostPosition costPosition = costPositionMapper.toEntity(costPositionDTO, taxMonth, costType);
        Long taxationFormId = businessService.getDTO(businessId).getTaxationFormId();
        TaxationForm taxationForm = taxationFormService.get(taxationFormId).get();
        costCalculationService.calculate(costPosition, taxationForm);
        costPositionRepository.save(costPosition);
    }

    @Override
    public List<CostPositionDTO> findAllCostPositions(Long taxMonthId) {
        return costPositionRepository.findAllByTaxMonthId(taxMonthId).stream().map(costPositionMapper::toDto).collect(Collectors.toList());
    }

}
