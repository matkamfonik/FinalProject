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
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.services.interfaces.BusinessService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;
import pl.coderslab.finalproject.services.interfaces.TaxationFormService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses")
public class BusinessViewController {
    private final BusinessService businessService;

    private final TaxationFormService taxationFormService;

    private final TaxYearService taxYearService;

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable(name = "id") Long id){
        BusinessDTO businessDTO = businessService.getDTO(id);
        List<TaxYearDTO> taxYears = taxYearService.findAllTaxYears(id);
        model.addAttribute("business", businessDTO);
        model.addAttribute("taxYears", taxYears);
        return "businesses/details";
    }

    @GetMapping("")
    public String add(Model model){
        List<TaxationForm> taxationForms = taxationFormService.getList();
        model.addAttribute("business", new BusinessDTO());
        model.addAttribute("taxationForms", taxationForms);
        return "businesses/add-form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id){
        businessService.delete(id);
        return "redirect:/view";
    }

    @PostMapping("")
    public String add(Model model,
                      @ModelAttribute(name = "business") @Valid BusinessDTO businessDTO,
                      BindingResult result,
                      @AuthenticationPrincipal CurrentUser currentUser){

        if (result.hasErrors()){
            List<TaxationForm> taxationForms = taxationFormService.getList();
            model.addAttribute("taxationForms", taxationForms);
            return "businesses/add-form";
        }

        businessService.add(businessDTO, currentUser);
        return "redirect:/view";
    }
}
