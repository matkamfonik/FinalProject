package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.finalproject.entities.TaxMonth;

import java.util.List;
import java.util.Optional;


public interface TaxMonthRepository extends JpaRepository <TaxMonth, Long> {

    List<TaxMonth> findByTaxYearIdOrderByNumberAsc(Long yearId);

    Optional<TaxMonth> findByTaxYearIdAndNumber(Long taxYearId, Integer monthNumber);

    List<TaxMonth> findByTaxYearIdAndNumberGreaterThan(Long taxYearId, Integer monthNumber);

    Optional<TaxMonth> findFirstByTaxYearIdOrderByNumberDesc(Long taxYearId);

    Optional<TaxMonth> findFirstByTaxYearYearAndNumber(Integer taxYearYear, Integer taxMonthNumber);

    @Query("SELECT tm FROM TaxMonth tm JOIN tm.taxYear ty WHERE (ty.id = :yearId AND tm.number > :taxMonthNumber) OR ty.year > :taxYearYear")
    List<TaxMonth> findNextMonths(@Param("yearId") Long yearId, @Param("taxMonthNumber") Integer taxMonthNumber, @Param("taxYearYear") Integer taxYearYear);

}
