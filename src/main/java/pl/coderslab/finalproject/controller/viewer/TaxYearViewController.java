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
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.mappers.TaxMonthMapper;
import pl.coderslab.finalproject.mappers.TaxYearMapper;
import pl.coderslab.finalproject.services.interfaces.BusinessService;
import pl.coderslab.finalproject.services.interfaces.TaxMonthService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years")
public class TaxYearViewController {

    private final TaxYearService taxYearService;

    private final TaxMonthService taxMonthService;

    private final TaxMonthMapper taxMonthMapper;

    private final TaxYearMapper taxYearMapper;

    private final BusinessService businessService;

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable(name = "businessId") Long businessId, @PathVariable(name = "id") Long id) {

        TaxYear taxYear = taxYearService.get(id).orElseThrow(EntityNotFoundException::new);
        TaxYearDTO taxYearDTO = taxYearMapper.toDto(taxYear);

        TaxationForm taxationForm = taxYear.getBusiness().getTaxationForm();

        TaxYear previousYear = taxYearService.findByYearAndBusinessId(taxYear.getYear() - 1, businessId).orElse(new TaxYear());
        TaxYearDTO previousYearDTO = taxYearMapper.toDto(previousYear);

        List<TaxMonth> taxMonths = taxMonthService.findByTaxYearIdOrderByNumberAsc(id);
        List<TaxMonthDTO> taxMonthDTOs = taxMonths.stream().map(taxMonthMapper::toDto).collect(Collectors.toList());

        model.addAttribute("taxYear", taxYearDTO);
        model.addAttribute("previousYear", previousYearDTO);
        model.addAttribute("taxMonths", taxMonthDTOs);
        model.addAttribute("businessId", businessId);
        model.addAttribute("taxationForm", taxationForm);
        return "tax-years/details";
    }

    @GetMapping("")
    public String add(Model model) {
        model.addAttribute("taxYear", new TaxYearDTO());
        return "tax-years/add-form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id,
                         @PathVariable(name = "businessId") Long businessId) {
        taxYearService.delete(id);
        return "redirect:/view/businesses/" + businessId;
    }

    @PostMapping("")
    public String add(@ModelAttribute(name = "taxYear") @Valid TaxYearDTO taxYearDTO,
                      BindingResult result,
                      @PathVariable(name = "businessId") Long businessId) {
        if (taxYearService.findByYearAndBusinessId(taxYearDTO.getYear(), businessId).isPresent())
            result.rejectValue("year", "error.taxYear", "Rok już istnieje");
        if (result.hasErrors()) {
            return "tax-years/add-form";
        }
        Business business = businessService.get(businessId).orElseThrow(EntityNotFoundException::new);
        TaxYear taxYear = taxYearMapper.toEntity(taxYearDTO, business);
        taxYearService.save(taxYear);

        return "redirect:/view/businesses/" + businessId;
    }

    @GetMapping("/{id}/patch")          // todo we viewerze musi tak zostać, w api controllerze ustawie na metodę patch
    public String patch(@PathVariable(name = "id") Long taxYearId,
                        @PathVariable(name = "businessId") Long businessId) {
        TaxYear taxYear = taxYearService.get(taxYearId).orElseThrow(EntityNotFoundException::new);
        taxYearService.update(taxYear);
        return "redirect:/view/businesses/" + businessId;
    }

}
