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
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.mappers.CostPositionMapper;
import pl.coderslab.finalproject.services.interfaces.CostPositionService;
import pl.coderslab.finalproject.services.interfaces.CostTypeService;
import pl.coderslab.finalproject.services.interfaces.TaxMonthService;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/businesses/{businessId}/tax-years/{taxYearId}/tax-months/{taxMonthId}/cost-positions")
public class CostPositionViewController {

    private final CostTypeService costTypeService;

    private final CostPositionMapper costPositionMapper;

    private final TaxMonthService taxMonthService;

    private final CostPositionService costPositionService;

    @GetMapping("")
    public String add(Model model){
        model.addAttribute("costPosition", new CostPositionDTO());
        model.addAttribute("costTypes", costTypeService.getList());
        return "cost-positions/add-form";
    }

    @PostMapping("")
    public String add(Model model,@ModelAttribute(name = "costPosition") @Valid CostPositionDTO costPositionDTO, BindingResult result, @PathVariable(name = "taxMonthId") Long taxMonthId){
        if (result.hasErrors()){
            model.addAttribute("costTypes", costTypeService.getList());
            return "cost-positions/add-form";
        }
        CostPosition costPosition = costPositionMapper.toEntity(costPositionDTO,
                taxMonthService.get(taxMonthId).get(),
                costTypeService.get(costPositionDTO.getCostTypeId()).get());

        costPositionService.save(costPosition);
        return "redirect:/view";
    }
}
