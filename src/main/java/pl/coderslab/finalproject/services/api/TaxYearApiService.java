package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.TaxYearMapper;
import pl.coderslab.finalproject.repository.TaxYearRepository;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxYearApiService implements TaxYearService {
    private final TaxYearRepository taxYearRepository;

    private final TaxYearMapper taxYearMapper;

    @Override
    public List<TaxYearDTO> findAllTaxYears(Long businessId) {
        return taxYearRepository.findAllTaxYearNameByBusinessIdOrderByYearAsc(businessId).stream().map(taxYearMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<TaxYear> findByYearAndBusinessId(int year, Long businessId) {
        return taxYearRepository.findByYearAndBusinessId(year, businessId);
    }

    @Override
    public List<TaxYear> findByBusinessIdAndYearGreaterThan(Long businessId, int year) {
        return taxYearRepository.findByBusinessIdAndYearGreaterThan(businessId, year);
    }


    @Override
    public Optional<TaxYear> get(Long id) {
        return taxYearRepository.findById(id);
    }

    @Override
    public void save(TaxYear taxYear) {
        taxYearRepository.save(taxYear);
    }
}
