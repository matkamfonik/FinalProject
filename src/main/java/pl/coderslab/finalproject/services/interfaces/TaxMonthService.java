package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxMonthService {

    List<TaxMonth> getList();

    Optional<TaxMonth> get(Long id);

    void save(TaxMonth taxMonth);

    List<TaxMonth> findByTaxYearIdOrderByNumberAsc(Long yearId);

    Optional<TaxMonth> findByTaxYearAndNumber(TaxYear taxYear, Integer monthNumber);

    List<TaxMonth> findByTaxYearIdAndNumberGreaterThan(Long taxYearId, Integer monthNumber);

    Optional<TaxMonth> findFirstByTaxYearIdOrderByNumberDesc(Long yearId);

    Optional<TaxMonth> findPrevious(Long taxMonthId);

    TaxMonthDTO update(Long taxMonthId, Long businessId);
}
