package pl.coderslab.finalproject.controller.viewer;

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
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.services.interfaces.BusinessService;
import pl.coderslab.finalproject.services.interfaces.TaxMonthService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years")
public class TaxYearViewController {

    private final TaxYearService taxYearService;

    private final BusinessService businessService;

    private final TaxMonthService taxMonthService;

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable(name = "businessId") Long businessId, @PathVariable(name = "id") Long id) {

        TaxYearDTO taxYearDTO = taxYearService.getDTO(id);

        TaxationForm taxationForm = businessService.get(businessId).get().getTaxationForm();

        TaxYearDTO previousYearDTO = taxYearService.findByYearAndBusinessId(taxYearDTO.getYear() - 1, businessId);

        List<TaxMonthDTO> taxMonths = taxMonthService.findByTaxYearIdOrderByNumberAsc(id);

        model.addAttribute("taxYear", taxYearDTO);
        model.addAttribute("previousYear", previousYearDTO);
        model.addAttribute("taxMonths", taxMonths);
        model.addAttribute("businessId", businessId);
        model.addAttribute("taxationForm", taxationForm);
        return "tax-years/details";
    }

    @GetMapping("")
    public String add(Model model) {
        model.addAttribute("taxYear", new TaxYearDTO());
        return "tax-years/add-form";
    }

    @PostMapping("")                            // todo walidacje zeby nie dodać takiego samego
    public String add(@Valid TaxYearDTO taxYearDTO,
                      BindingResult result,
                      @PathVariable(name = "businessId") Long businessId) {
        if (result.hasErrors()) {
            return "tax-years/add-form";
        }
        taxYearService.save(taxYearDTO, businessId);

        return "redirect:/view/businesses/" + businessId;
    }
//    @GetMapping("/{id}/patch")          // we viewerze musi tak zostać, w api controllerze ustawie na metodę patch
//    public String patch(){
//
//        return "forward:";
//    }

}
