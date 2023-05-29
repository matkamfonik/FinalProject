package pl.coderslab.finalproject.services.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.services.api.TaxMonthApiService;
import pl.coderslab.finalproject.services.api.TaxYearApiService;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class TaxYearCalculationService {

    private final TaxYearApiService taxYearService;

    private final TaxMonthApiService taxMonthService;

    public TaxYear calculate(Long taxYearId, Long businessId) {
        TaxYear taxYear = taxYearService.get(taxYearId).get();
        TaxYear previousTaxYear = taxYearService.findByYearAndBusinessId(taxYear.getYear() - 1, businessId).orElse(new TaxYear());
        taxYear.setBalance(BigDecimal.valueOf(0L, 2));
        taxYear.setVatBalance(previousTaxYear.getVatBalance());
        taxMonthService.findByTaxYearIdOrderByNumberAsc(taxYearId)
                .forEach(tm -> {
                    taxYear.setBalance(taxYear.getBalance().add(tm.getIncome(), new MathContext(5)));
                    if (taxYear.getVatBalance().compareTo(BigDecimal.ZERO) > 0 ){
                        taxYear.setVatBalance(BigDecimal.valueOf(0L, 2));
                    }
                    taxYear.setVatBalance(taxYear.getVatBalance().add(tm.getVatValue(), new MathContext(5)));
                });
        return taxYear;

    }
}
