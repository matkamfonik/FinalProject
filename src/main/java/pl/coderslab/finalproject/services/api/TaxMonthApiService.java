package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.TaxMonthMapper;
import pl.coderslab.finalproject.repository.TaxMonthRepository;
import pl.coderslab.finalproject.services.calculation.TaxMonthCalculationService;
import pl.coderslab.finalproject.services.interfaces.TaxMonthService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxMonthApiService implements TaxMonthService {
    private final TaxMonthRepository taxMonthRepository;

    private final TaxYearApiService taxYearService;

    private final TaxMonthCalculationService taxMonthCalculationService;

    private final TaxMonthMapper taxMonthMapper;

    @Override
    public List<TaxMonth> getList() {
        return taxMonthRepository.findAll();
    }

    @Override
    public List<TaxMonth> findByTaxYearIdOrderByNumberAsc(Long yearId) {
        return taxMonthRepository.findByTaxYearIdOrderByNumberAsc(yearId);
    }

    @Override
    public Optional<TaxMonth> findByTaxYearAndNumber(TaxYear taxYear, Integer monthNumber) {
        return taxMonthRepository.findByTaxYearAndNumber(taxYear, monthNumber);
    }

    @Override
    public List<TaxMonth> findByTaxYearIdAndNumberGreaterThan(Long taxYearId, Integer monthNumber) {
        return taxMonthRepository.findByTaxYearIdAndNumberGreaterThan(taxYearId, monthNumber);
    }

    @Override
    public Optional<TaxMonth> findFirstByTaxYearIdOrderByNumberDesc(Long taxYearId) {
        return taxMonthRepository.findFirstByTaxYearIdOrderByNumberDesc(taxYearId);
    }

    @Override
    public Optional<TaxMonth> findPrevious(Long taxMonthId) {
        TaxMonth taxMonth = taxMonthRepository.findById(taxMonthId).get();
        Optional<TaxMonth> previousTaxMonth;
        if (taxMonth.getNumber() == 1) {
            previousTaxMonth = taxMonthRepository.findFirstByTaxYearYearAndNumber(taxMonth.getTaxYear().getYear() - 1, 12);
        } else {
            previousTaxMonth = taxMonthRepository.findByTaxYearAndNumber(taxMonth.getTaxYear(), taxMonth.getNumber() - 1);
        }
        return previousTaxMonth;
    }

    @Override
    public TaxMonthDTO update(Long taxMonthId, Long businessId) {
        TaxMonth taxMonth = taxMonthCalculationService.calculate(taxMonthId, businessId);
        taxMonthRepository.save(taxMonth);
        return taxMonthMapper.toDto(this.get(taxMonthId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Optional<TaxMonth> get(Long id) {
        return taxMonthRepository.findById(id);
    }

    @Override
    public void save(TaxMonth taxMonth) {
        taxMonthRepository.save(taxMonth);
    }

    public void setNextMonthsNotUpToDate(Long taxMonthId, Long businessId, Long taxYearId) {
        List<TaxYear> newerYears = taxYearService.findByBusinessIdAndYearGreaterThan(businessId, taxYearService.get(taxYearId).get().getYear());
        newerYears.forEach(t -> {
            t.setUpToDate(false);
            taxYearService.save(t);
            this.findByTaxYearIdOrderByNumberAsc(t.getId()).forEach(m -> {
                m.setUpToDate(false);
                this.save(m);
            });
        });
        List<TaxMonth> nextMonths = this.findByTaxYearIdAndNumberGreaterThan(taxYearId, this.get(taxMonthId).get().getNumber());
        nextMonths.forEach(m -> {
            m.setUpToDate(false);
            this.save(m);
        });
    }
}
