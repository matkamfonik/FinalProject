package pl.coderslab.finalproject.services.interfaces;

import org.springframework.data.repository.query.Param;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxMonthService {

    TaxMonthDTO getDTO(Long taxMonthId);

    Optional<TaxMonth> get(Long taxMonthId);

    void add(TaxMonthDTO taxMonthDTO, TaxYear taxYear);

    List<TaxMonthDTO> findByTaxYearIdOrderByNumberAsc(Long yearId);

    List<TaxMonthDTO> findByTaxYearIdAndNumberGreaterThan(Long taxYearId, Integer monthNumber);

    TaxMonthDTO findFirstByTaxYearIdOrderByNumberDesc(Long yearId);

    Optional<TaxMonth> findPrevious(Long taxMonthId);

    void update(Long taxMonthId, Long businessId);

    void patch(TaxMonthDTO taxMonthDTO, TaxYear taxYear);

    List<TaxMonth> findNextMonths (Long yearId, Integer taxMonthNumber, Integer taxYearYear);

    void setNextMonthsNotUpToDate(Long taxMonthId, TaxYear taxYear); //todo wyrzucić stąd po zrobieniu TaxMonthApiControllera
}
