package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.repository.TaxMonthRepository;
import pl.coderslab.finalproject.repository.TaxYearRepository;
import pl.coderslab.finalproject.services.interfaces.TaxMonthService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxMonthApiService implements TaxMonthService {
    private final TaxMonthRepository taxMonthRepository;

    @Override
    public List<TaxMonth> getList() {
        return taxMonthRepository.findAll();
    }

    @Override
    public List<TaxMonth> findByTaxYearIdOrderByNumberDesc(Long yearId) {
        return taxMonthRepository.findByTaxYearIdOrderByNumberDesc(yearId);
    }

    @Override
    public TaxMonth findByTaxYearAndNumber(TaxYear taxYear, Integer monthNumber) {
        return taxMonthRepository.findByTaxYearAndNumber(taxYear, monthNumber);
    }

    @Override
    public Optional<TaxMonth> get(Long id) {
        return taxMonthRepository.findById(id);
    }

    @Override
    public void save(TaxMonth taxMonth) {
        taxMonthRepository.save(taxMonth);
    }




}
