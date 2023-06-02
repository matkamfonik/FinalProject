package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.repository.CostPositionRepository;
import pl.coderslab.finalproject.services.calculation.CostCalculationService;
import pl.coderslab.finalproject.services.interfaces.CostPositionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CostPositionApiService implements CostPositionService {
    private final CostPositionRepository costPositionRepository;

    private final CostCalculationService costCalculationService;

    @Override
    public Optional<CostPosition> get(Long id) {
        return costPositionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        costPositionRepository.deleteById(id);
    }

    @Override
    public void add(CostPosition costPosition) {
        costCalculationService.calculate(costPosition);
        costPositionRepository.save(costPosition);
    }

    @Override
    public List<CostPosition> findCostPositions(Long taxMonthId) {
        return costPositionRepository.findAllByTaxMonthId(taxMonthId);
    }

}
