package pl.coderslab.finalproject.services.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.services.api.*;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class CostCalculationService {

    private final BusinessApiService businessService;

    private final TaxationFormApiService taxationFormService;


    public void calculateHealthInsurance (CostPositionDTO costPositionDTO, Long businessId, int taxYearYear, TaxMonth previousTaxMonth){
        if(costPositionDTO == null){
            costPositionDTO = new CostPositionDTO();
        }
        costPositionDTO.setName("Składka zdrowotna");
        costPositionDTO.setCostTypeId(1L);
        costPositionDTO.setVatRate(BigDecimal.valueOf(0L, 2));
        BigDecimal healthInsurance = BigDecimal.valueOf(0L, 2);
        Long taxationFormId = businessService.get(businessId).get().getTaxationForm().getId();
        TaxationForm taxationForm = taxationFormService.get(taxationFormId).get();
        BigDecimal healthCareContributionRate = taxationForm.getHealthCareContributionRate();
        BigDecimal previousMonthIncome = previousTaxMonth.getIncome();
        healthInsurance = healthInsurance.add(healthCareContributionRate.divide(BigDecimal.valueOf(100L), new MathContext(5)).multiply(previousMonthIncome, new MathContext(5)), new MathContext(5));
        if (taxYearYear < 2023 && healthInsurance.longValue() < 270.90) {
            costPositionDTO.setNetto(BigDecimal.valueOf(270.9));
        } else if (taxYearYear >= 2023 && healthInsurance.longValue() < 314.1) {
            costPositionDTO.setNetto(BigDecimal.valueOf(314.1));
        } else {
            costPositionDTO.setNetto(healthInsurance);
        }
    }

    public void calculate(CostPosition costPosition, TaxationForm taxationForm) {
        costPosition.setVat(costPosition.getNetto().multiply(costPosition.getVatRate().divide(BigDecimal.valueOf(100L), new MathContext(5)), new MathContext(5)));
        costPosition.setBrutto(costPosition.getNetto().add(costPosition.getVat(), new MathContext(5)));
        CostType costType = costPosition.getCostType();
        costPosition.setVatDeducted(costPosition.getVat().multiply(costType.getVatCostRate(), new MathContext(5)));
        if (costType.getId().equals(1L)) {
            if (taxationForm.getHealthContributionAsCost()) {
                costPosition.setCostIncluded(costPosition.getBrutto());
            } else {
                costPosition.setCostIncluded(BigDecimal.valueOf(0L, 2));
            }
        } else {
            costPosition.setCostIncluded(costPosition.getBrutto()
                    .subtract(costPosition.getVatDeducted(), new MathContext(5))
                    .multiply(costType.getCostRate(), new MathContext(5))
                    .multiply(BigDecimal.valueOf(taxationForm.getCostRate()), new MathContext(5))
            );
        }
    }
}
