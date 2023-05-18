package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxYearService {

    List<TaxYear> getList();

    List<TaxYear> findAllTaxYearByBusinessIdOrderByYearAsc(Long businessId);

    Optional<TaxYear> findByYearAndBusinessId(int year, Long businessId);

    Optional<TaxYear> get(Long id);

    void add(TaxYear taxYear);


}
