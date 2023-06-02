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
import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.services.interfaces.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months")
public class TaxMonthViewController {

    private final TaxMonthService taxMonthService;

    private final CostPositionService costPositionService;

    private final RevenuePositionService revenuePositionService;

    private final BusinessService businessService;

    private final TaxYearService taxYearService;

    @GetMapping("/{id}")
    public String show(Model model,
                       @PathVariable(name = "id") Long id,
                       @PathVariable(name = "taxYearId") Long taxYearId,
                       @PathVariable(name = "businessId") Long businessId) {

        TaxMonthDTO taxMonthDTO = taxMonthService.getDTO(id);

        TaxationForm taxationForm = businessService.get(businessId).get().getTaxationForm();

        List<CostPositionDTO> costPositions = costPositionService.findAllCostPositions(id);
        List<RevenuePositionDTO> revenuePositions = revenuePositionService.findAllRevenuePositions(id);

        model.addAttribute("taxMonth", taxMonthDTO);
        model.addAttribute("costPositions", costPositions);
        model.addAttribute("revenuePositions", revenuePositions);
        model.addAttribute("taxYearId", taxYearId);
        model.addAttribute("businessId", businessId);
        model.addAttribute("taxationForm", taxationForm);

        return "tax-months/details";
    }

    @GetMapping("")
    public String add(Model model,
                      @PathVariable(name = "taxYearId") Long taxYearId,
                      @PathVariable(name = "businessId") Long businessId) {
        Integer previousMonthNumber = taxMonthService.findFirstByTaxYearIdOrderByNumberDesc(taxYearId).getNumber();
        TaxMonthDTO taxMonthDTO = new TaxMonthDTO();
        if (previousMonthNumber > 11) {
            return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId;
        }
        taxMonthDTO.setNumber(previousMonthNumber + 1);
        model.addAttribute("taxMonth", taxMonthDTO);
        return "tax-months/add-form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id,
                         @PathVariable(name = "taxYearId") Long taxYearId,
                         @PathVariable(name = "businessId") Long businessId) {

        taxMonthService.delete(id);

        taxYearService.update(taxYearId, businessId);

        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId;
    }

    @PostMapping("")
    public String add(@ModelAttribute(name = "taxMonth") @Valid TaxMonthDTO taxMonthDTO,
                      BindingResult result,
                      @PathVariable(name = "taxYearId") Long taxYearId,
                      @PathVariable(name = "businessId") Long businessId) {
        if (taxMonthService.findByTaxYearIdAndNumber(taxYearId, taxMonthDTO.getNumber()).isPresent()) {
            result.rejectValue("number", "error.taxMonth", "Miesiąc już istnieje");
        }
        if (result.hasErrors()) {
            return "tax-months/add-form";
        }
        TaxYear taxYear = taxYearService.get(taxYearId).get();
        taxMonthService.add(taxMonthDTO, taxYear);


        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId;
    }

    @GetMapping("/{id}/patch")
    public String patch(@PathVariable(name = "id") Long taxMonthId,
                        @PathVariable(name = "taxYearId") Long taxYearId,
                        @PathVariable(name = "businessId") Long businessId) {
        taxMonthService.update(taxMonthId, businessId);
        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId;
    }

}
