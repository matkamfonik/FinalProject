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
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.entities.*;
import pl.coderslab.finalproject.mappers.CostPositionMapper;
import pl.coderslab.finalproject.mappers.RevenuePositionMapper;
import pl.coderslab.finalproject.mappers.TaxMonthMapper;
import pl.coderslab.finalproject.services.interfaces.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months")
public class TaxMonthViewController {

    private final TaxMonthService taxMonthService;

    private final CostPositionService costPositionService;

    private final RevenuePositionService revenuePositionService;

    private final TaxYearService taxYearService;

    private final CostPositionMapper costPositionMapper;

    private final RevenuePositionMapper revenuePositionMapper;

    private final TaxMonthMapper taxMonthMapper;

    @GetMapping("/{id}")
    public String show(Model model,
                       @PathVariable(name = "id") Long id,
                       @PathVariable(name = "taxYearId") Long taxYearId,
                       @PathVariable(name = "businessId") Long businessId) {

        TaxMonth taxMonth = taxMonthService.get(id).orElseThrow(EntityNotFoundException::new);
        TaxMonthDTO taxMonthDTO = taxMonthMapper.toDto(taxMonth);
        TaxationForm taxationForm = taxMonth.getTaxYear().getBusiness().getTaxationForm();
        List<CostPosition> costPositions = costPositionService.findCostPositions(id);
        List<CostPositionDTO> positionDTOs = costPositions.stream().map(costPositionMapper::toDto).collect(Collectors.toList());
        List<RevenuePosition> revenuePositions = revenuePositionService.findRevenuePositions(id);
        List<RevenuePositionDTO> revenuePositionDTOs = revenuePositions.stream().map(revenuePositionMapper::toDto).collect(Collectors.toList());

        model.addAttribute("taxMonth", taxMonthDTO);
        model.addAttribute("costPositions", positionDTOs);
        model.addAttribute("revenuePositions", revenuePositionDTOs);
        model.addAttribute("taxYearId", taxYearId);
        model.addAttribute("businessId", businessId);
        model.addAttribute("taxationForm", taxationForm);

        return "tax-months/details";
    }

    @GetMapping("")
    public String add(Model model,
                      @PathVariable(name = "taxYearId") Long taxYearId,
                      @PathVariable(name = "businessId") Long businessId) {
        Integer previousMonthNumber = taxMonthService.findFirstByTaxYearIdOrderByNumberDesc(taxYearId).map(TaxMonth::getNumber).orElse(0);
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
        TaxYear taxYear = taxYearService.get(taxYearId).orElseThrow(EntityNotFoundException::new);
        taxYearService.update(taxYear);               // todo wywalić do api controller

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
        TaxYear taxYear = taxYearService.get(taxYearId).orElseThrow(EntityNotFoundException::new);
        TaxMonth taxMonth = taxMonthMapper.toEntity(taxMonthDTO, taxYear);
        taxMonthService.add(taxMonth);

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
