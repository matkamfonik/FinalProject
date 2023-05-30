package pl.coderslab.finalproject.services.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxMonth;
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

    private final TaxMonthApiService taxMonthService;

    private final BusinessApiService businessService;

    private final TaxationFormApiService taxationFormService;

    public TaxMonth calculate(Long taxMonthId, Long businessId) {
        TaxMonth taxMonth = taxMonthService.get(taxMonthId).get();
        taxMonth.setCosts(BigDecimal.valueOf(0L, 2));
        taxMonth.setRevenue(BigDecimal.valueOf(0L, 2));
        taxMonth.setVatValue(BigDecimal.valueOf(0L, 2));
        taxMonth.setSocialInsurance(BigDecimal.valueOf(0L, 2));
        costPositionService.findAllCostPositions(taxMonthId)
                .stream()
                .map(cp -> {
                    taxMonth.setCosts(taxMonth.getCosts().add(cp.getCostIncluded(), new MathContext(5)));
                    taxMonth.setVatValue(taxMonth.getVatValue().subtract(cp.getVatDeducted(), new MathContext(5)));
                    return cp;
                })
                .filter(cp -> cp.getCostTypeId() == 1L || cp.getCostTypeId() == 2L)
                .forEach(cp -> {
                    taxMonth.setSocialInsurance(taxMonth.getSocialInsurance().add(cp.getNetto(), new MathContext(5)));
                });
        revenuePositionService.findAllRevenuePositions(taxMonthId)
                .forEach(rp -> {
                    taxMonth.setRevenue(taxMonth.getRevenue().add(rp.getNetto(), new MathContext(5)));
                    taxMonth.setVatValue(taxMonth.getVatValue().add(rp.getVat(), new MathContext(5)));
                });
        taxMonth.setIncome(taxMonth.getRevenue().subtract(taxMonth.getCosts(), new MathContext(5)));
        Long taxationFormId = businessService.get(businessId).getTaxationFormId();
        BigDecimal pitRate = taxationFormService.get(taxationFormId).get().getPitRate();
        taxMonth.setPitValue(taxMonth.getIncome().multiply(pitRate.divide(BigDecimal.valueOf(100L), new MathContext(5)), new MathContext(5)));
        if (taxMonthService.findPrevious(taxMonthId).orElse(new TaxMonth()).getUpToDate()){
            taxMonth.setUpToDate(true);
        }
        return taxMonth;
    }
}
