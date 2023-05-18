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
import org.springframework.web.service.annotation.HttpExchange;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.Client;
import pl.coderslab.finalproject.mappers.BusinessMapper;
import pl.coderslab.finalproject.mappers.ClientMapper;
import pl.coderslab.finalproject.services.interfaces.BusinessService;
import pl.coderslab.finalproject.services.interfaces.ClientService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;
import pl.coderslab.finalproject.services.interfaces.TaxationFormService;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/clients")
public class ClientViewController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable(name = "id") Long id){

        model.addAttribute("client", clientMapper.toDto(clientService.get(id).orElseThrow(EntityNotFoundException::new)));
        return "clients/details";
    }

    @GetMapping("/all")
    public String list(Model model, @AuthenticationPrincipal CurrentUser currentUser){
        model.addAttribute("clients", clientService.findAllClientNameByUserId(currentUser.getUser().getId()));
        return "clients/all";
    }

    @GetMapping("")
    public String add(Model model){
        model.addAttribute("client", new ClientDTO());
        return "clients/add-form";
    }

    @PostMapping("")
    public String add(@ModelAttribute(name = "client") @Valid ClientDTO clientDTO, BindingResult result, @AuthenticationPrincipal CurrentUser currentUser){
        if (result.hasErrors()){
            return "clients/add-form";
        }
        Client client = clientMapper.toEntity(clientDTO,
                currentUser.getUser());

        clientService.add(client);
        return "redirect:/";
    }

    @GetMapping("/nip")
    public String getByNip(Model model){
        
        return "clients/add-form";
    }
}
