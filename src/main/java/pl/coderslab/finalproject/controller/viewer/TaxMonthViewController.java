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
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.TaxMonthMapper;
import pl.coderslab.finalproject.services.calculation.TaxMonthCalculationService;
import pl.coderslab.finalproject.services.interfaces.*;

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

    private final CostPositionService costPositionService;

    private final RevenuePositionService revenuePositionService;

    private final BusinessService businessService;

    private final TaxMonthCalculationService taxMonthCalculationService;

    @GetMapping("/{id}")                        //todo powinno przechodzic przez update gdzie oblicza wszytskie pozycje
    public String show(Model model,
                       @PathVariable(name = "id") Long id,
                       @PathVariable(name = "taxYearId") Long taxYearId,
                       @PathVariable(name = "businessId") Long businessId){
        TaxMonth taxMonth = taxMonthCalculationService.calculate(id, businessId);
        taxMonthService.save(taxMonth);
        TaxMonthDTO taxMonthDTO = taxMonthMapper.toDto(taxMonthService.get(id).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("taxMonth", taxMonthDTO);
        model.addAttribute("costPositions", costPositionService.findAllByTaxMonthId(id));
        model.addAttribute("revenuePositions", revenuePositionService.findAllByTaxMonthId(id));
        model.addAttribute("taxYearId", taxYearId);
        model.addAttribute("businessId", businessId);
        model.addAttribute("taxationForm", businessService.get(businessId).get().getTaxationForm());



        return "tax-months/details";
    }

    @GetMapping("")
    public String add(Model model,
                      @PathVariable(name = "taxYearId") Long taxYearId,
                      @PathVariable(name = "businessId") Long businessId){
        Integer previousMonthNumber = taxMonthService.findFirstByTaxYearIdOrderByNumberDesc(taxYearId)
                .map(TaxMonth::getNumber)
                .orElse(0);
        TaxMonthDTO taxMonthDTO = new TaxMonthDTO();
        if (previousMonthNumber > 11){
            return "redirect:/view/businesses/"+businessId+"/tax-years/"+taxYearId;
        }
        taxMonthDTO.setNumber(previousMonthNumber+1);
        model.addAttribute("taxMonth", taxMonthDTO);
        return "tax-months/add-form";
    }

    @PostMapping("")                            // todo walidacje zeby nie dodać takiego samego
    public String add(@Valid TaxMonthDTO taxMonthDTO,
                      BindingResult result,
                      @PathVariable(name = "taxYearId") Long taxYearId,
                      @PathVariable(name = "businessId") Long businessId){
        if (result.hasErrors()){
            return "tax-years/add-form";
        }
        TaxYear taxYear = taxYearService.get(taxYearId).get();
        TaxMonth taxMonth = taxMonthMapper.toEntity(taxMonthDTO,
                taxYear);

        List<TaxYear> newerYears = taxYearService.findByBusinessIdAndYearGreaterThan(businessId, taxYear.getYear());
        newerYears.forEach(ty -> {
            ty.setUpToDate(false);
            taxYearService.save(ty);
            taxMonthService.findByTaxYearIdOrderByNumberAsc(ty.getId()).forEach(m -> {
                m.setUpToDate(false);
                taxMonthService.save(m);
            });
        });
        List<TaxMonth> nextMonths = taxMonthService.findByTaxYearIdAndNumberGreaterThan(taxYearId, taxMonth.getNumber());
        nextMonths.forEach(m-> {
            m.setUpToDate(false);
            taxMonthService.save(m);
        });

        taxMonthService.save(taxMonth);
        return "redirect:/view/businesses/"+businessId+"/tax-years/"+taxYearId;
    }

}
