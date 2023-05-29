package pl.coderslab.finalproject.controller.viewer;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.services.calculation.RevenueCalculationService;
import pl.coderslab.finalproject.services.interfaces.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months/{taxMonthId}/revenue-positions")
public class RevenuePositionViewController {

    private final RevenueCalculationService revenueCalculationService;

    private final RevenuePositionService revenuePositionService;

    private final TaxYearService taxYearService;

    private final TaxMonthService taxMonthService;

    @GetMapping("")
    public String add(Model model) {
        model.addAttribute("revenuePosition", new RevenuePositionDTO());
        return "revenue-positions/add-form";
    }

    @PostMapping("")
    public String add(Model model,
                      @ModelAttribute(name = "revenuePosition") @Valid RevenuePositionDTO revenuePositionDTO,
                      BindingResult result,
                      @PathVariable(name = "taxMonthId") Long taxMonthId,
                      @PathVariable(name = "businessId") Long businessId,
                      @PathVariable(name = "taxYearId") Long taxYearId) {
        if (result.hasErrors()) {
            return "cost-positions/add-form";
        }
        RevenuePosition revenuePosition = revenueCalculationService.calculate(revenuePositionDTO, taxMonthId);
        revenuePositionService.save(revenuePosition);

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
}
