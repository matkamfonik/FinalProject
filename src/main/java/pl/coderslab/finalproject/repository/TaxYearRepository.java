package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;


public interface TaxYearRepository extends JpaRepository <TaxYear, Long> {

    List<TaxYear> findAllTaxYearNameByBusinessIdOrderByYearAsc(Long businessId);

    Optional<TaxYear> findByYearAndBusinessId(int year, Long businessId);

    List<TaxYear> findByBusinessIdAndYearGreaterThan(Long businessId, int year);

}
