package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxMonthService {

    List<TaxMonth> getList();

    Optional<TaxMonth> get(Long id);

    void save(TaxMonth taxMonth);

    List<TaxMonth> findByTaxYearIdOrderByNumberDesc(Long yearId);


}