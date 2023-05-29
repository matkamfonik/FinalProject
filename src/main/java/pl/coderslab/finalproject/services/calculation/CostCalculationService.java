package pl.coderslab.finalproject.services.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.mappers.CostPositionMapper;
import pl.coderslab.finalproject.services.api.BusinessApiService;
import pl.coderslab.finalproject.services.api.CostTypeApiService;
import pl.coderslab.finalproject.services.api.TaxMonthApiService;
import pl.coderslab.finalproject.services.api.TaxYearApiService;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class CostCalculationService {

    private final BusinessApiService businessService;

    private final TaxMonthApiService taxMonthService;

    private final TaxYearApiService taxYearService;

    private final CostPositionMapper costPositionMapper;

    private final CostTypeApiService costTypeService;


    public CostPositionDTO calculateHealthInsurance (Long taxMonthId, Long businessId){
        CostPositionDTO costPositionDTO = new CostPositionDTO();
        costPositionDTO.setName("Składka zdrowotna");
        costPositionDTO.setCostTypeId(1L);
        costPositionDTO.setVatRate(BigDecimal.valueOf(0L, 2));
        TaxMonth taxMonth = taxMonthService.get(taxMonthId).get();
        Integer monthNumber = taxMonth.getNumber();
        TaxYear taxYear = taxMonth.getTaxYear();
        BigDecimal healthInsurance = BigDecimal.valueOf(0L, 2);
        TaxationForm taxationForm = businessService.get(businessId).get().getTaxationForm();
        BigDecimal healthCareContributionRate = taxationForm.getHealthCareContributionRate();
        BigDecimal previousMonthIncome;
        if (monthNumber > 1) {
            previousMonthIncome = taxMonthService.findByTaxYearAndNumber(taxYear, monthNumber - 1).map(TaxMonth::getIncome).orElse(BigDecimal.valueOf(0L, 2));
        } else {
            TaxYear preciousTaxYear = taxYearService.findByYearAndBusinessId(taxYearService.get(taxYear.getId()).get().getYear() - 1, businessId).orElse(null);
            previousMonthIncome = taxMonthService.findByTaxYearAndNumber(preciousTaxYear, 12).map(TaxMonth::getIncome).orElse(BigDecimal.valueOf(0L, 2));
        }
        healthInsurance = healthInsurance.add(healthCareContributionRate.divide(BigDecimal.valueOf(100L), new MathContext(5)).multiply(previousMonthIncome, new MathContext(5)), new MathContext(5));
        if (taxYear.getYear() < 2023 && healthInsurance.longValue() < 270.90) {
            costPositionDTO.setNetto(BigDecimal.valueOf(270.9));
        } else if (taxYear.getYear() >= 2023 && healthInsurance.longValue() < 314.1) {
            costPositionDTO.setNetto(BigDecimal.valueOf(314.1));
        } else {
            costPositionDTO.setNetto(healthInsurance);
        }
        return costPositionDTO;
    }

    public CostPosition calculate(CostPositionDTO costPositionDTO, Long taxMonthId, Long businessId) {
        CostPosition costPosition = costPositionMapper.toEntity(costPositionDTO,
                taxMonthService.get(taxMonthId).get(),
                costTypeService.get(costPositionDTO.getCostTypeId()).get());
        costPosition.setVat(costPosition.getNetto().multiply(costPosition.getVatRate().divide(BigDecimal.valueOf(100L), new MathContext(5)), new MathContext(5)));
        costPosition.setBrutto(costPosition.getNetto().add(costPosition.getVat(), new MathContext(5)));
        CostType costType = costPosition.getCostType();
        costPosition.setVatDeducted(costPosition.getVat().multiply(costType.getVatCostRate(), new MathContext(5)));
        TaxationForm taxationForm = businessService.get(businessId).get().getTaxationForm();
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
        return costPosition;
    }
}
