package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.RevenuePosition;
import pl.coderslab.finalproject.repository.RevenuePositionRepository;
import pl.coderslab.finalproject.services.calculation.RevenueCalculationService;
import pl.coderslab.finalproject.services.interfaces.RevenuePositionService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class RevenuePositionApiService implements RevenuePositionService {
    private final RevenuePositionRepository revenuePositionRepository;

    private final RevenueCalculationService revenueCalculationService;

    @Override
    public Optional<RevenuePosition> get(Long id) {
        return revenuePositionRepository.findById(id);
    }

    @Override
    public void add(RevenuePosition revenuePosition) {
        revenueCalculationService.calculate(revenuePosition);
        revenuePositionRepository.save(revenuePosition);

    }

    @Override
    public void delete(Long id) {
        revenuePositionRepository.deleteById(id);
    }

    @Override
    public List<RevenuePosition> findRevenuePositions(Long taxMonthId) {
        return revenuePositionRepository.findAllByTaxMonthId(taxMonthId);
    }


}
