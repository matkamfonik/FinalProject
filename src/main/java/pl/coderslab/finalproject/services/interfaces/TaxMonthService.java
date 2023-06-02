package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxMonthService {

    Optional<TaxMonth> get(Long taxMonthId);

    void add(TaxMonth taxMonth);

    void delete(Long id);

    List<TaxMonth> findByTaxYearIdOrderByNumberAsc(Long yearId);

    Optional<TaxMonth> findFirstByTaxYearIdOrderByNumberDesc(Long yearId);

    Optional<TaxMonth> findPrevious(Long taxMonthId);

    void update(Long taxMonthId, Long businessId);

    void patch(TaxMonth taxMonth);

    List<TaxMonth> findNextMonths (Long yearId, Integer taxMonthNumber, Integer taxYearYear);

    void setNextMonthsNotUpToDate(Long taxMonthId, TaxYear taxYear); //todo wyrzucić stąd po zrobieniu TaxMonthApiControllera

    Optional<TaxMonth> findByTaxYearIdAndNumber(Long taxYearId, Integer monthNumber);
}
