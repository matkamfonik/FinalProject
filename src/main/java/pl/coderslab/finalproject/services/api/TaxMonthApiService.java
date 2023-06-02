package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.entities.TaxationForm;
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

    private final TaxMonthCalculationService taxMonthCalculationService;


    @Override
    public List<TaxMonth> findByTaxYearIdOrderByNumberAsc(Long yearId) {
        return taxMonthRepository.findByTaxYearIdOrderByNumberAsc(yearId);
    }

    @Override
    public Optional<TaxMonth> findFirstByTaxYearIdOrderByNumberDesc(Long taxYearId) {
        return taxMonthRepository.findFirstByTaxYearIdOrderByNumberDesc(taxYearId);
    }

    @Override
    public Optional<TaxMonth> findPrevious(Long taxMonthId) {
        TaxMonth taxMonth = taxMonthRepository.findById(taxMonthId).orElse(new TaxMonth());
        Optional<TaxMonth> previousTaxMonth;
        if (taxMonth.getNumber() > 1) {
            previousTaxMonth = taxMonthRepository.findByTaxYearIdAndNumber(taxMonth.getTaxYear().getId(), taxMonth.getNumber() - 1);
        } else {
            previousTaxMonth = taxMonthRepository.findFirstByTaxYearYearAndNumber(taxMonth.getTaxYear().getYear() - 1, 12);
        }
        return previousTaxMonth;
    }

    @Override
    public void update(Long taxMonthId, Long businessId) {
        TaxMonth taxMonth = this.get(taxMonthId).orElseThrow(EntityNotFoundException::new);
        TaxationForm taxationForm = taxMonth.getTaxYear().getBusiness().getTaxationForm();
        TaxMonth previousMonth = this.findPrevious(taxMonth.getId()).orElse(new TaxMonth());
        taxMonthCalculationService.calculate(taxMonth, taxationForm, previousMonth);
        taxMonthRepository.save(taxMonth);
        this.setNextMonthsNotUpToDate(taxMonthId, taxMonth.getTaxYear());
    }

    @Override
    public Optional<TaxMonth> get(Long taxMonthId) {
        return taxMonthRepository.findById(taxMonthId);
    }

    @Override
    public void add(TaxMonth taxMonth) {

        taxMonthRepository.save(taxMonth);

        this.setNextMonthsNotUpToDate(taxMonth.getId(), taxMonth.getTaxYear());
    }

    @Override
    public void delete(Long id){

        this.setNextMonthsNotUpToDate(id, this.get(id).map(TaxMonth::getTaxYear).orElseThrow(EntityNotFoundException::new));

        taxMonthRepository.deleteById(id);

    }

    @Override
    public void patch(TaxMonth taxMonth) {
        taxMonthRepository.save(taxMonth);
    }

    @Override
    public List<TaxMonth> findNextMonths(Long yearId, Integer taxMonthNumber, Integer taxYearYear) {
        return taxMonthRepository.findNextMonths(yearId, taxMonthNumber, taxYearYear);
    }


    public void setNextMonthsNotUpToDate(Long taxMonthId, TaxYear taxYear) {
        TaxMonth taxMonth = this.get(taxMonthId).orElseThrow(EntityNotFoundException::new);
        this.findNextMonths(taxMonth.getTaxYear().getId(), taxMonth.getNumber(), taxMonth.getTaxYear().getYear())
                .forEach(tm -> {
                    tm.setUpToDate(false);
                    this.patch(tm);
                });
    }

    @Override
    public Optional<TaxMonth> findByTaxYearIdAndNumber(Long taxYearId, Integer monthNumber) {
        return taxMonthRepository.findByTaxYearIdAndNumber(taxYearId, monthNumber);
    }


}
