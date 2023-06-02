package pl.coderslab.finalproject.services.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.services.api.*;

import java.math.BigDecimal;
import java.math.MathContext;

@RequiredArgsConstructor
@Getter
@Setter
@Service
public class TaxMonthCalculationService {

    private final CostPositionApiService costPositionService;

    private final RevenuePositionApiService revenuePositionService;

    public void calculate(TaxMonth taxMonth, TaxationForm taxationForm, TaxMonth previousMonth) {
        taxMonth.setCosts(BigDecimal.valueOf(0L, 2));
        taxMonth.setRevenue(BigDecimal.valueOf(0L, 2));
        taxMonth.setVatValue(BigDecimal.valueOf(0L, 2));
        taxMonth.setSocialInsurance(BigDecimal.valueOf(0L, 2));
        costPositionService.findCostPositions(taxMonth.getId())
                .stream()
                .peek(cp -> {
                    taxMonth.setCosts(taxMonth.getCosts().add(cp.getCostIncluded(), new MathContext(5)));
                    taxMonth.setVatValue(taxMonth.getVatValue().subtract(cp.getVatDeducted(), new MathContext(5)));
                })
                .filter(cp -> cp.getCostType().getId() == 1L || cp.getCostType().getId() == 2L)
                .forEach(cp -> taxMonth.setSocialInsurance(taxMonth.getSocialInsurance().add(cp.getNetto(), new MathContext(5))));
        revenuePositionService.findAllRevenuePositions(taxMonth.getId())
                .forEach(rp -> {
                    taxMonth.setRevenue(taxMonth.getRevenue().add(rp.getNetto(), new MathContext(5)));
                    taxMonth.setVatValue(taxMonth.getVatValue().add(rp.getVat(), new MathContext(5)));
                });
        taxMonth.setIncome(taxMonth.getRevenue().subtract(taxMonth.getCosts(), new MathContext(5)));
        BigDecimal pitRate = taxationForm.getPitRate();
        taxMonth.setPitValue(taxMonth.getIncome().multiply(pitRate.divide(BigDecimal.valueOf(100L), new MathContext(5)), new MathContext(5)));
        if (previousMonth.getUpToDate()) {
            taxMonth.setUpToDate(true);
        }

    }
}
