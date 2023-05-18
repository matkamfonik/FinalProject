package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;


public interface TaxYearRepository extends JpaRepository <TaxYear, Long> {

    public List<TaxYear> findAllTaxYearNameByBusinessId(Long businessId);

}
