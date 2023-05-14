package pl.coderslab.finalproject.controller.viewer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.services.interfaces.TaxationFormService;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/taxation-forms")
public class TaxationFormViewController {
    private final TaxationFormService taxationFormService;

    @GetMapping("all")
    public String list(Model model){
        model.addAttribute("taxationForms", taxationFormService.getList());
        return "taxation-forms/all";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("taxationForm", taxationFormService.get(id).orElseThrow(EntityNotFoundException::new));
        return "taxation-forms/details";
    }

    @GetMapping("")
    public String add(Model model){
        model.addAttribute("taxationForm", new TaxationForm());
        return "taxation-forms/add-form";
    }

    @PostMapping("")
    public String add(@Valid TaxationForm taxationForm, BindingResult result){
        if (result.hasErrors()){
            return "taxation-forms/add-form";
        }
        taxationFormService.add(taxationForm);
        return "redirect:taxation-forms/all";
    }
}
