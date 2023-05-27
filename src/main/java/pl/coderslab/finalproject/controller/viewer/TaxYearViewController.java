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
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.TaxYearMapper;
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

    private final TaxYearMapper taxYearMapper;

    private final BusinessService businessService;

    private final TaxMonthService taxMonthService;

    @GetMapping("/{id}")
    public String show(Model model,@PathVariable(name = "businessId") Long businessId,  @PathVariable(name = "id") Long id){
        TaxYearDTO taxYearDTO = taxYearMapper.toDto(taxYearService.get(id).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("taxYear", taxYearDTO);
        model.addAttribute("previousYear", taxYearMapper.toDto(taxYearService.findByYearAndBusinessId(taxYearDTO.getYear()-1, businessId).orElse(new TaxYear())));
        model.addAttribute("taxMonths", taxMonthService.findByTaxYearIdOrderByNumberDesc(id));
        model.addAttribute("businessId", businessId);
        return "tax-years/details";
    }

    @GetMapping("")
    public String add(Model model){
        model.addAttribute("taxYear", new TaxYearDTO());
        return "tax-years/add-form";
    }

    @PostMapping("")                            // todo walidacje zeby nie dodać takiego samego
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

        List<TaxYear> newerYears = taxYearService.findByBusinessIdAndYearGreaterThan(businessId, taxYearDTO.getYear());
        newerYears.forEach(t -> {
            t.setUpToDate(false);
            taxYearService.save(t);
        });

        taxYearService.save(taxYear);
        return "redirect:/view/businesses/"+businessId;
    }
//    @GetMapping("/{id}/patch")          // we viewerze musi tak zostać, w api controllerze ustawie na metodę patch
//    public String patch(){
//
//        return "forward:";
//    }

}
