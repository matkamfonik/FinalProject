package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.RevenuePosition;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.mappers.RevenuePositionMapper;
import pl.coderslab.finalproject.repository.RevenuePositionRepository;
import pl.coderslab.finalproject.services.calculation.RevenueCalculationService;
import pl.coderslab.finalproject.services.interfaces.RevenuePositionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class RevenuePositionApiService implements RevenuePositionService {
    private final RevenuePositionRepository revenuePositionRepository;

    private final RevenueCalculationService revenueCalculationService;

    private final RevenuePositionMapper revenuePositionMapper;

    @Override
    public RevenuePositionDTO getDTO(Long id) {
        return revenuePositionMapper.toDto(revenuePositionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void add(RevenuePositionDTO revenuePositionDTO, TaxMonth taxMonth) {
        RevenuePosition revenuePosition = revenuePositionMapper.toEntity(revenuePositionDTO, taxMonth);
        revenueCalculationService.calculate(revenuePosition);
        revenuePositionRepository.save(revenuePosition);

    }

    @Override
    public void delete(Long id){
        revenuePositionRepository.deleteById(id);
    }

    @Override
    public List<RevenuePositionDTO> findAllRevenuePositions(Long taxMonthId) {
        return revenuePositionRepository.findAllByTaxMonthId(taxMonthId).stream().map(revenuePositionMapper::toDto).collect(Collectors.toList());
    }


}
