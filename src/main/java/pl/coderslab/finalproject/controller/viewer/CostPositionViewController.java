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
import pl.coderslab.finalproject.entities.CostType;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.services.calculation.CostCalculationService;
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

    private final CostPositionService costPositionService;

    private final CostCalculationService costCalculationService;

    private final TaxMonthService taxMonthService;

    private final TaxYearService taxYearService;

    @GetMapping("")
    public String add(Model model,
                      @PathVariable(name = "businessId") Long businessId,
                      @PathVariable(name = "taxYearId") Long taxYearId,
                      @PathVariable(name = "taxMonthId") Long taxMonthId) {
        List<CostType> costTypes = costTypeService.getList();
        model.addAttribute("costPosition", new CostPositionDTO());
        model.addAttribute("costTypes", costTypes);
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
            List<CostType> costTypes = costTypeService.getList();
            model.addAttribute("costTypes", costTypes);
            model.addAttribute("taxMonthId", taxMonthId);
            model.addAttribute("taxYearId", taxYearId);
            model.addAttribute("businessId", businessId);
            return "cost-positions/add-form";
        }
        TaxMonth taxMonth = taxMonthService.get(taxMonthId).get();
        costPositionService.add(costPositionDTO, taxMonth, businessId, taxYearId);
        TaxYear taxYear = taxYearService.get(taxYearId).get();                      //todo przenieść do posta w ApiControllerze
        taxMonthService.setNextMonthsNotUpToDate(taxMonthId, taxYear);              //todo przenieść do posta w ApiControllerze
        taxMonthService.update(taxMonthId, businessId);                             //todo przenieść do posta w ApiControllerze
        taxYearService.update(taxYearId, businessId);                               //todo przenieść do posta w ApiControllerze


        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId + "/tax-months/" + taxMonthId;
    }



    @GetMapping("/health-insurance")
    public String calculateHealthInsurance(Model model,
                                           @PathVariable(name = "taxMonthId") Long taxMonthId,
                                           @PathVariable(name = "businessId") Long businessId,
                                           @PathVariable(name = "taxYearId") Long taxYearId) {
        TaxMonth previousTaxMonth = taxMonthService.findPrevious(taxMonthId).orElse(new TaxMonth());
        int taxYearYear = taxYearService.get(taxYearId).get().getYear();
        CostPositionDTO costPositionDTO = costCalculationService.calculateHealthInsurance(businessId, taxYearYear, previousTaxMonth);
        List<CostType> costTypes = costTypeService.getList();
        model.addAttribute("costPosition", costPositionDTO);
        model.addAttribute("taxYearId", taxYearId);
        model.addAttribute("costTypes", costTypes);

        return "cost-positions/add-form";
    }


}
