package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;

import java.util.List;
import java.util.Optional;


public interface TaxMonthRepository extends JpaRepository <TaxMonth, Long> {

    public List<TaxMonth> findByTaxYearIdOrderByNumberDesc(Long yearId);

    public TaxMonth findByTaxYearAndNumber(TaxYear taxYear, Integer monthNumber);

}
