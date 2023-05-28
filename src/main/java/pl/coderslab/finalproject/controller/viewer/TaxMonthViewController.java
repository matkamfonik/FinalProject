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
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.TaxMonthMapper;
import pl.coderslab.finalproject.services.interfaces.BusinessService;
import pl.coderslab.finalproject.services.interfaces.TaxMonthService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months")
public class TaxMonthViewController {

    private final TaxYearService taxYearService;

    private final TaxMonthMapper taxMonthMapper;

    private final TaxMonthService taxMonthService;

//    @GetMapping("/{id}")
//    public String show(Model model,@PathVariable(name = "businessId") Long businessId,@PathVariable(name = "taxYearId") Long taxYearId,  @PathVariable(name = "id") Long id){
//        TaxMonthDTO taxMonthDTO = taxMonthMapper.toDto(taxMonthService.get(id).orElseThrow(EntityNotFoundException::new));
//        model.addAttribute("taxMonth", taxMonthDTO);
//        model.addAttribute("previousYear", taxMonthMapper.toDto(taxYearService.findByYearAndBusinessId(taxYearDTO.getYear()-1, businessId).orElse(new TaxYear())));
//        model.addAttribute("taxMonths", taxMonthService.findByTaxYearIdOrderByNumberDesc(id));
//        model.addAttribute("businessId", businessId);
//        return "tax-years/details";
//    }

//    @GetMapping("")
//    public String add(Model model){
//        model.addAttribute("taxYear", new TaxYearDTO());
//        return "tax-years/add-form";
//    }

//    @PostMapping("")                            // todo walidacje zeby nie dodać takiego samego
//    public String add(@Valid TaxYearDTO taxYearDTO,
//                      BindingResult result,
//                      @AuthenticationPrincipal CurrentUser currentUser,
//                      @PathVariable(name = "businessId") Long businessId){
//        if (result.hasErrors()){
//            return "tax-years/add-form";
//        }
//        TaxYear taxYear = taxMonthMapper.toEntity(taxYearDTO,
//                currentUser.getUser(),
//                businessService.get(businessId).get());
//
//        List<TaxYear> newerYears = taxYearService.findByBusinessIdAndYearGreaterThan(businessId, taxYearDTO.getYear());
//        newerYears.forEach(t -> {
//            t.setUpToDate(false);
//            taxYearService.save(t);
//        });
//
//        taxYearService.save(taxYear);
//        return "redirect:/view/businesses/"+businessId;
//    }

}
