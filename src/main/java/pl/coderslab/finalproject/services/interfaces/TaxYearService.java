package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;

public interface TaxYearService {

    List<TaxYear> findAllTaxYears(Long businessId);

    TaxYearDTO findByYearAndBusinessId(int year, Long businessId);

    List<TaxYearDTO> findByBusinessIdAndYearGreaterThan(Long businessId, int year);

    TaxYearDTO getDTO(Long id);

    Optional<TaxYear> get(Long id);

    void save(TaxYearDTO taxYearDTO, Long businessId);

    void delete(Long id);

    void update(Long taxYearId, Long businessId);

    void patch(TaxYearDTO taxYearDTO, Long businessId);
}
