package pl.coderslab.finalproject.controller.viewer;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.mappers.CostPositionMapper;
import pl.coderslab.finalproject.services.interfaces.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months/{taxMonthId}/cost-positions")
public class CostPositionViewController {

    private final CostTypeService costTypeService;

    private final CostPositionMapper costPositionMapper;

    private final TaxMonthService taxMonthService;

    private final CostPositionService costPositionService;

    private final BusinessService businessService;

    private final TaxYearService taxYearService;

    @GetMapping("")
    public String add(Model model,
                      @PathVariable(name = "businessId") Long businessId,
                      @PathVariable(name = "taxYearId") Long taxYearId,
                      @PathVariable(name = "taxMonthId") Long taxMonthId) {
        model.addAttribute("costPosition", new CostPositionDTO());
        model.addAttribute("costTypes", costTypeService.getList());
        model.addAttribute("taxMonthId", taxMonthId);
        model.addAttribute("taxYearId", taxYearId);
        model.addAttribute("businessId", businessId);
        return "cost-positions/add-form";
    }

    @PostMapping("")
    public String add(Model model,
                      @ModelAttribute(name = "costPosition") @Valid CostPositionDTO costPositionDTO,
                      BindingResult result, @PathVariable(name = "taxMonthId") Long taxMonthId,
                      @PathVariable(name = "businessId") Long businessId,
                      @PathVariable(name = "taxYearId") Long taxYearId) {
        if (result.hasErrors()) {
            model.addAttribute("costTypes", costTypeService.getList());
            model.addAttribute("taxMonthId", taxMonthId);
            model.addAttribute("taxYearId", taxYearId);
            model.addAttribute("businessId", businessId);
            return "cost-positions/add-form";
        }
        CostPosition costPosition = getCostPosition(costPositionDTO, taxMonthId, businessId);
        costPositionService.save(costPosition);
        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId + "/tax-months/" + taxMonthId;
    }

    private CostPosition getCostPosition(CostPositionDTO costPositionDTO, Long taxMonthId, Long businessId) {
        CostPosition costPosition = costPositionMapper.toEntity(costPositionDTO,
                taxMonthService.get(taxMonthId).get(),
                costTypeService.get(costPositionDTO.getCostTypeId()).get());
        costPosition.setVat(costPosition.getNetto().multiply(costPosition.getVatRate()).divide(BigDecimal.valueOf(100L), new MathContext(2)));
        costPosition.setBrutto(costPosition.getNetto().add(costPosition.getVat()));
        CostType costType = costPosition.getCostType();
        costPosition.setVatDeducted(costPosition.getVat().multiply(costType.getVatCostRate()));
        TaxationForm taxationForm = businessService.get(businessId).get().getTaxationForm();
        if (costType.getId().equals(1L)) {
            if (taxationForm.getHealthContributionAsCost()) {
                costPosition.setCostIncluded(costPosition.getBrutto());
            } else {
                costPosition.setCostIncluded(BigDecimal.ZERO);
            }
        } else {
            costPosition.setCostIncluded(costPosition.getBrutto()
                    .subtract(costPosition.getVatDeducted())
                    .multiply(costType.getCostRate())
                    .multiply(BigDecimal.valueOf(taxationForm.getCostRate()))
            );
        }
        return costPosition;
    }

    @GetMapping("/health-insurance")
    public String calculateHealthInsurance(Model model,
                                           @PathVariable(name = "taxMonthId") Long taxMonthId,
                                           @PathVariable(name = "businessId") Long businessId,
                                           @PathVariable(name = "taxYearId") Long taxYearId) {
        TaxMonth taxMonth = taxMonthService.get(taxMonthId).get();
        CostPositionDTO costPositionDTO = calculateHealthInsurance(taxMonth, businessId);
        model.addAttribute("costPosition", costPositionDTO);
        model.addAttribute("taxYearId", taxYearId);
        model.addAttribute("costTypes", costTypeService.getList());
        List<TaxYear> newerYears = taxYearService.findByBusinessIdAndYearGreaterThan(businessId, taxMonth.getTaxYear().getYear());
        newerYears.forEach(t -> {
            t.setUpToDate(false);
            taxYearService.save(t);
            taxMonthService.findByTaxYearIdOrderByNumberDesc(t.getId()).forEach(m -> {
                m.setUpToDate(false);
                taxMonthService.save(m);
            });
        });
        List<TaxMonth> nextMonths = taxMonthService.findByTaxYearIdAndNumberGreaterThan(taxYearId, taxMonth.getNumber());
        nextMonths.forEach(m -> {
            m.setUpToDate(false);
            taxMonthService.save(m);
        });

        return "cost-positions/add-form";
    }


    private CostPositionDTO calculateHealthInsurance (TaxMonth taxMonth, Long businessId){
        CostPositionDTO costPositionDTO = new CostPositionDTO();
        costPositionDTO.setName("Składka zdrowotna");
        costPositionDTO.setCostTypeId(1L);
        costPositionDTO.setVatRate(BigDecimal.ZERO);
        Integer monthNumber = taxMonth.getNumber();
        TaxYear taxYear = taxMonth.getTaxYear();
        BigDecimal healthInsurance = BigDecimal.ZERO;
        TaxationForm taxationForm = businessService.get(businessId).get().getTaxationForm();
        BigDecimal healthCareContributionRate = taxationForm.getHealthCareContributionRate();
        BigDecimal previousMonthIncome;
        if (monthNumber > 1) {
            previousMonthIncome = taxMonthService.findByTaxYearAndNumber(taxYear, monthNumber - 1).map(TaxMonth::getIncome).orElse(BigDecimal.ZERO);
        } else {
            TaxYear preciousTaxYear = taxYearService.findByYearAndBusinessId(taxYearService.get(taxYear.getId()).get().getYear() - 1, businessId).orElse(null);
            previousMonthIncome = taxMonthService.findByTaxYearAndNumber(preciousTaxYear, 12).map(TaxMonth::getIncome).orElse(BigDecimal.ZERO);
        }
        healthInsurance = healthInsurance.add(healthCareContributionRate.divide(BigDecimal.valueOf(100L), new MathContext(2)).multiply(previousMonthIncome));
        if (taxYear.getYear() < 2023 && healthInsurance.longValue() < 270.90) {
            costPositionDTO.setNetto(BigDecimal.valueOf(270.9));
        } else if (taxYear.getYear() >= 2023 && healthInsurance.longValue() < 314.1) {
            costPositionDTO.setNetto(BigDecimal.valueOf(314.1));
        } else {
            costPositionDTO.setNetto(healthInsurance);
        }
        return costPositionDTO;
    }
}
