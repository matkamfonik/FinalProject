package pl.coderslab.finalproject.services.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class TaxYearCalculationService {


    public void calculate(TaxYear taxYear, TaxYear previousTaxYear, List<TaxMonth> months) {

        taxYear.setBalance(BigDecimal.valueOf(0L, 2));
        taxYear.setVatBalance(previousTaxYear.getVatBalance());

        months.forEach(tm -> {
            taxYear.setBalance(taxYear.getBalance().add(tm.getIncome(), new MathContext(5)));
            if (taxYear.getVatBalance().compareTo(BigDecimal.ZERO) > 0) {
                taxYear.setVatBalance(BigDecimal.valueOf(0L, 2));
            }
            taxYear.setVatBalance(taxYear.getVatBalance().add(tm.getVatValue(), new MathContext(5)));
        });
        if (previousTaxYear.getUpToDate() && months.stream().filter(tm -> !tm.getUpToDate()).findAny().isEmpty()) {
            taxYear.setUpToDate(true);
        }
    }
}
