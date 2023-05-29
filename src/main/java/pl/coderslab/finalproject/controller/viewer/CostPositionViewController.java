package pl.coderslab.finalproject.controller.viewer;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.services.calculation.CostCalculationService;
import pl.coderslab.finalproject.services.interfaces.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months/{taxMonthId}/cost-positions")
public class CostPositionViewController {

    private final CostTypeService costTypeService;

    private final TaxMonthService taxMonthService;

    private final CostPositionService costPositionService;

    private final TaxYearService taxYearService;

    private final CostCalculationService costCalculationService;

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
        CostPosition costPosition = costCalculationService.calculate(costPositionDTO, taxMonthId, businessId);
        costPositionService.save(costPosition);

        setNextMonthsNotUpToDate(taxMonthId, businessId, taxYearId);

        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId + "/tax-months/" + taxMonthId;
    }

    private void setNextMonthsNotUpToDate(Long taxMonthId, Long businessId, Long taxYearId) {
        List<TaxYear> newerYears = taxYearService.findByBusinessIdAndYearGreaterThan(businessId, taxYearService.get(taxYearId).get().getYear());
        newerYears.forEach(t -> {
            t.setUpToDate(false);
            taxYearService.save(t);
            taxMonthService.findByTaxYearIdOrderByNumberAsc(t.getId()).forEach(m -> {
                m.setUpToDate(false);
                taxMonthService.save(m);
            });
        });
        List<TaxMonth> nextMonths = taxMonthService.findByTaxYearIdAndNumberGreaterThan(taxYearId, taxMonthService.get(taxMonthId).get().getNumber());
        nextMonths.forEach(m -> {
            m.setUpToDate(false);
            taxMonthService.save(m);
        });
    }

    @GetMapping("/health-insurance")
    public String calculateHealthInsurance(Model model,
                                           @PathVariable(name = "taxMonthId") Long taxMonthId,
                                           @PathVariable(name = "businessId") Long businessId,
                                           @PathVariable(name = "taxYearId") Long taxYearId) {
        CostPositionDTO costPositionDTO = costCalculationService.calculateHealthInsurance(taxMonthId, businessId);
        model.addAttribute("costPosition", costPositionDTO);
        model.addAttribute("taxYearId", taxYearId);
        model.addAttribute("costTypes", costTypeService.getList());

        return "cost-positions/add-form";
    }


}
