package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.entities.RevenuePosition;
import pl.coderslab.finalproject.repository.RevenuePositionRepository;
import pl.coderslab.finalproject.services.interfaces.RevenuePositionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class RevenuePositionApiService implements RevenuePositionService {
    private final RevenuePositionRepository revenuePositionRepository;

    @Override
    public List<RevenuePosition> getList() {
        return revenuePositionRepository.findAll();
    }

    @Override
    public Optional<RevenuePosition> get(Long id) {
        return revenuePositionRepository.findById(id);
    }

    @Override
    public void save(RevenuePosition revenuePosition) {
        revenuePositionRepository.save(revenuePosition);
    }

    @Override
    public List<RevenuePosition> findAllByTaxMonthId(Long taxMonthId) {
        return revenuePositionRepository.findAllByTaxMonthId(taxMonthId);
    }
}
