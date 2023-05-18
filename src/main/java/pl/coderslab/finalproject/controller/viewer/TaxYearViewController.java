package pl.coderslab.finalproject.controller.viewer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.TaxYearMapper;
import pl.coderslab.finalproject.services.interfaces.BusinessService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years")
public class TaxYearViewController {

    private final TaxYearService taxYearService;

    private final TaxYearMapper taxYearMapper;

    private final BusinessService businessService;

    @GetMapping("/{id}")
    public String show(Model model,@PathVariable(name = "businessId") Long businessId,  @PathVariable(name = "id") Long id){
        TaxYearDTO taxYearDTO = taxYearMapper.toDto(taxYearService.get(id).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("taxYear", taxYearDTO);
        model.addAttribute("previousYear", taxYearMapper.toDto(taxYearService.findFirstByYearBeforeAndBusinessId(taxYearDTO.getYear(), businessId).orElse(new TaxYear())));
        return "tax-years/details";
    }

    @GetMapping("")
    public String add(Model model){
        model.addAttribute("taxYear", new TaxYearDTO());
        return "tax-years/add-form";
    }

    @PostMapping("")
    public String add(@Valid TaxYearDTO taxYearDTO,
                      BindingResult result,
                      @AuthenticationPrincipal CurrentUser currentUser,
                      @PathVariable(name = "businessId") Long businessId){
        if (result.hasErrors()){
            return "tax-years/add-form";
        }
        TaxYear taxYear = taxYearMapper.toEntity(taxYearDTO,
                currentUser.getUser(),
                businessService.get(businessId).get());

        taxYearService.add(taxYear);
        return "redirect:/view/businesses/"+businessId;
    }
}
