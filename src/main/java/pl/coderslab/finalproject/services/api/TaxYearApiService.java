package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.repository.TaxYearRepository;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxYearApiService implements TaxYearService {
    private final TaxYearRepository taxYearRepository;

    @Override
    public List<TaxYear> getList() {
        return taxYearRepository.findAll();
    }

    @Override
    public List<TaxYear> findAllTaxYearByBusinessIdOrderByYearAsc(Long businessId) {
        return taxYearRepository.findAllTaxYearNameByBusinessIdOrderByYearAsc(businessId);
    }

    @Override
    public Optional<TaxYear> findByYearAndBusinessId(int year, Long businessId) {
        return taxYearRepository.findByYearAndBusinessId(year, businessId);
    }


    @Override
    public Optional<TaxYear> get(Long id) {
        return taxYearRepository.findById(id);
    }

    @Override
    public void add(TaxYear taxYear) {
        taxYearRepository.save(taxYear);
    }
}
