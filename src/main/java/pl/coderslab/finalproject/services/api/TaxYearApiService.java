package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.repository.TaxYearRepository;
import pl.coderslab.finalproject.services.calculation.TaxYearCalculationService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxYearApiService implements TaxYearService {
    private final TaxYearRepository taxYearRepository;

    private final TaxYearCalculationService taxYearCalculationService;

    private final TaxMonthApiService taxMonthService;

    @Override
    public List<TaxYear> findAllTaxYears(Long businessId) {
        return taxYearRepository.findAllTaxYearNameByBusinessIdOrderByYearAsc(businessId);
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
    public Optional<TaxYear> get(Long id){
        return taxYearRepository.findById(id);
    }

    @Override
    public void save(TaxYear taxYear) {
        Long businessId = taxYear.getBusiness().getId();
        TaxYear previousTaxYear = this.findByYearAndBusinessId(taxYear.getYear() - 1, businessId).orElse(new TaxYear());
        List<TaxMonth> taxMonths = taxMonthService.findByTaxYearIdOrderByNumberAsc(taxYear.getId());
        taxYearCalculationService.calculate(taxYear, previousTaxYear, taxMonths);
        taxYearRepository.save(taxYear);

        this.setNextMonthsNotUpToDate(taxYear);
    }

    @Override
    public void delete(Long id){
        TaxYear taxYear = this.get(id).orElseThrow(EntityNotFoundException::new);
        this.setNextMonthsNotUpToDate(taxYear);
        taxYearRepository.deleteById(id);
    }

    @Override
    public void update(TaxYear taxYear){
        TaxYear previousTaxYear = this.findByYearAndBusinessId(taxYear.getYear() - 1, taxYear.getBusiness().getId()).orElse(new TaxYear());
        List<TaxMonth> months = taxMonthService.findByTaxYearIdOrderByNumberAsc(taxYear.getId());
        taxYearCalculationService.calculate(taxYear, previousTaxYear, months);
        taxYearRepository.save(taxYear);
        this.setNextMonthsNotUpToDate(taxYear);
    }

    public void setNextMonthsNotUpToDate(TaxYear taxYear) {
        List<TaxYear> newerYears = this.findByBusinessIdAndYearGreaterThan(taxYear.getBusiness().getId(), taxYear.getYear());
        newerYears.forEach(ty -> {
            ty.setUpToDate(false);
            this.patch(ty);
            taxMonthService.findByTaxYearIdOrderByNumberAsc(ty.getId()).forEach(tm -> {
                tm.setUpToDate(false);
                taxMonthService.patch(tm);
            });
        });
    }
    @Override
    public void patch(TaxYear taxYear){
        taxYearRepository.save(taxYear);
    }

}
