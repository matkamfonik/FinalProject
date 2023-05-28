package pl.coderslab.finalproject.controller.viewer;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.mappers.CostPositionMapper;
import pl.coderslab.finalproject.services.interfaces.*;

import java.math.BigDecimal;
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
    public String add(Model model){
        model.addAttribute("costPosition", new CostPositionDTO());
        model.addAttribute("costTypes", costTypeService.getList());
        return "cost-positions/add-form";
    }

    @PostMapping("")
    public String add(Model model,@ModelAttribute(name = "costPosition") @Valid CostPositionDTO costPositionDTO, BindingResult result, @PathVariable(name = "taxMonthId") Long taxMonthId){
        if (result.hasErrors()){
            model.addAttribute("costTypes", costTypeService.getList());
            return "cost-positions/add-form";
        }
        CostPosition costPosition = costPositionMapper.toEntity(costPositionDTO,
                taxMonthService.get(taxMonthId).get(),
                costTypeService.get(costPositionDTO.getCostTypeId()).get());

        costPositionService.save(costPosition);
        return "redirect:/view";
    }

    @GetMapping("/health-insurance")
    public String calculateHealthInsurance(Model model,
                                           @PathVariable(name = "taxMonthId") Long taxMonthId,
                                           @PathVariable(name = "businessId") Long businessId){
        CostPositionDTO costPositionDTO = new CostPositionDTO();
        costPositionDTO.setName("Składka zdrowotna");
        costPositionDTO.setCostTypeId(1L);
        costPositionDTO.setVatRate(BigDecimal.ZERO);
        TaxMonth taxMonth = taxMonthService.get(taxMonthId).get();
        Integer monthNumber = taxMonth.getNumber();
        TaxYear taxYear = taxMonth.getTaxYear();
        BigDecimal healthInsurance = BigDecimal.ZERO;
        TaxationForm taxationForm = businessService.get(businessId).get().getTaxationForm();
        BigDecimal healthCareContributionRate = taxationForm.getHealthCareContributionRate();
        BigDecimal previousMonthIncome = BigDecimal.ZERO;
        if (monthNumber > 1){
            previousMonthIncome = taxMonthService.findByTaxYearAndNumber(taxYear, monthNumber - 1).getIncome();
        } else {
            TaxYear preciousTaxYear = taxYearService.findByYearAndBusinessId(taxYearService.get(taxYear.getId()).get().getYear() - 1, businessId).orElse(new TaxYear());
            previousMonthIncome = taxMonthService.findByTaxYearAndNumber(preciousTaxYear, 12).getIncome();
        }
        healthInsurance = healthInsurance.add(healthCareContributionRate.divide(BigDecimal.valueOf(100L)).multiply(previousMonthIncome));
        if (taxYear.getYear() < 2023 && healthInsurance.longValue() < 270.90){
            costPositionDTO.setBrutto(BigDecimal.valueOf(270.9));
        } else if (taxYear.getYear() >2023 && healthInsurance.longValue() < 314.1) {
            costPositionDTO.setBrutto(BigDecimal.valueOf(314.1));
        } else {
            costPositionDTO.setBrutto(healthInsurance);
        }
        model.addAttribute("costPosition", costPositionDTO);
        model.addAttribute("costTypes", costTypeService.getList());

        List<TaxYear> newerYears = taxYearService.findByBusinessIdAndYearGreaterThan(businessId, taxYear.getYear());
        newerYears.forEach(t -> {
            t.setUpToDate(false);
            taxYearService.save(t);
            taxMonthService.findByTaxYearIdOrderByNumberDesc(t.getId()).forEach(m -> m.setUpToDate(false));
        });

        POWTÓRZYĆ DLA MIESIECY W DANYM ROKU
        return "cost-positions/add-form";
    }
}
