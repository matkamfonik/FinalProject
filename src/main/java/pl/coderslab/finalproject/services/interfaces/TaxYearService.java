package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxYearService {

    List<TaxYear> getList();

    List<TaxYear> findAllTaxYearByBusinessId(Long businessId);

    Optional<TaxYear> get(Long id);

    void add(TaxYear taxYear);


}
