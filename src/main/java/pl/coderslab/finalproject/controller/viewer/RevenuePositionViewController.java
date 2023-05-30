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
import pl.coderslab.finalproject.services.interfaces.*;


@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months/{taxMonthId}/revenue-positions")
public class RevenuePositionViewController {


    private final RevenuePositionService revenuePositionService;

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
        revenuePositionService.save(revenuePositionDTO, taxMonthId, businessId, taxYearId);

        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId + "/tax-months/" + taxMonthId;
    }


}
