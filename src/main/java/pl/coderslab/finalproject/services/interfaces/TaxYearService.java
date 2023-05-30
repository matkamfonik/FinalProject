package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxYearService {

    List<TaxYearDTO> findAllTaxYears(Long businessId);

    Optional<TaxYear> findByYearAndBusinessId(int year, Long businessId);

    List<TaxYear> findByBusinessIdAndYearGreaterThan(Long businessId, int year);

    Optional<TaxYear> get(Long id);

    void save(TaxYear taxYear);


}
