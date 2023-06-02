package pl.coderslab.finalproject.controller.viewer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.RevenuePosition;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.RevenuePositionMapper;
import pl.coderslab.finalproject.services.interfaces.*;


@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months/{taxMonthId}/revenue-positions")
public class RevenuePositionViewController {

    private final RevenuePositionService revenuePositionService;

    private final TaxMonthService taxMonthService;

    private final TaxYearService taxYearService;

    private final RevenuePositionMapper revenuePositionMapper;

    @GetMapping("")
    public String add(Model model) {
        model.addAttribute("revenuePosition", new RevenuePositionDTO());
        return "revenue-positions/add-form";
    }

    @GetMapping("/{id}")
    public String edit(Model model,
                       @PathVariable(name = "id") Long id) {
        RevenuePosition revenuePosition = revenuePositionService.get(id).orElseThrow(EntityNotFoundException::new);
        RevenuePositionDTO revenuePositionDTO = revenuePositionMapper.toDto(revenuePosition);
        model.addAttribute("revenuePosition", revenuePositionDTO);
        return "revenue-positions/add-form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id,
                         @PathVariable(name = "taxMonthId") Long taxMonthId,
                         @PathVariable(name = "taxYearId") Long taxYearId,
                         @PathVariable(name = "businessId") Long businessId){

        revenuePositionService.delete(id);

        TaxYear taxYear = taxYearService.get(taxYearId).orElseThrow(EntityNotFoundException::new);
        taxMonthService.setNextMonthsNotUpToDate(taxMonthId, taxYear);                                              //todo przenieść do api controller
        taxMonthService.update(taxMonthId, businessId);
        taxYearService.update(taxYear);

        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId + "/tax-months/" +taxMonthId;
    }

    @PostMapping(value = {"", "/{id}"})
    public String add(@ModelAttribute(name = "revenuePosition") @Valid RevenuePositionDTO revenuePositionDTO,
                      BindingResult result,
                      @PathVariable(name = "taxMonthId") Long taxMonthId,
                      @PathVariable(name = "businessId") Long businessId,
                      @PathVariable(name = "taxYearId") Long taxYearId) {
        if (result.hasErrors()) {
            return "cost-positions/add-form";
        }
        TaxMonth taxMonth = taxMonthService.get(taxMonthId).orElseThrow(EntityNotFoundException::new);
        RevenuePosition revenuePosition = revenuePositionMapper.toEntity(revenuePositionDTO, taxMonth);
        revenuePositionService.add(revenuePosition);

        TaxYear taxYear = taxYearService.get(taxYearId).orElseThrow(EntityNotFoundException::new);                      //todo przenieść do posta w ApiControllerze
        taxMonthService.setNextMonthsNotUpToDate(taxMonthId, taxYear);                                                   //
        taxMonthService.update(taxMonthId, businessId);                                                                   //
        taxYearService.update(taxYear);                                                                     //

        return "redirect:/view/businesses/" + businessId + "/tax-years/" + taxYearId + "/tax-months/" + taxMonthId;
    }

}
