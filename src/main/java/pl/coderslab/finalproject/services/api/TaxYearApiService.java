package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.repository.BusinessRepository;
import pl.coderslab.finalproject.repository.TaxYearRepository;
import pl.coderslab.finalproject.services.interfaces.BusinessService;
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
    public List<TaxYear> findAllTaxYearByBusinessId(Long businessId) {
        return taxYearRepository.findAllTaxYearNameByBusinessId(businessId);
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
