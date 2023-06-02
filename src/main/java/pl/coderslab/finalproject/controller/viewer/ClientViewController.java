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
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.httpClients.BlockFirmy;
import pl.coderslab.finalproject.httpClients.ClientClient;
import pl.coderslab.finalproject.httpClients.NipRegon;
import pl.coderslab.finalproject.services.interfaces.ClientService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Controller
@RequestMapping("/view/clients")
public class ClientViewController {
    private final ClientService clientService;

    private final ClientClient clientClient;

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable(name = "id") Long id) {
        ClientDTO clientDTO = clientService.getDTO(id);
        model.addAttribute("client", clientDTO);
        return "clients/details";
    }

    @GetMapping("/all")
    public String list(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        List<ClientDTO> clients = clientService.findAllClients(currentUser);
        model.addAttribute("clients", clients);
        return "clients/all";
    }

    @GetMapping("")
    public String add(Model model) {
        model.addAttribute("client", new ClientDTO());
        model.addAttribute("nipRegon", new NipRegon());
        return "clients/add-form";
    }

    @PostMapping("")
    public String add(@ModelAttribute(name = "client") @Valid ClientDTO clientDTO,
                      BindingResult result,
                      @AuthenticationPrincipal CurrentUser currentUser) {
        if (result.hasErrors()) {
            return "clients/add-form";
        }
        clientService.add(clientDTO, currentUser);
        return "redirect:/view";
    }

    @GetMapping("/nip")
    public String getByNip(Model model,
                           @ModelAttribute(name = "nipRegon") @Valid NipRegon nipRegon,
                           BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("client", new ClientDTO());
            return "clients/add-form";
        }
        BlockFirmy blockFirmy = clientClient.getByNip(nipRegon.getNipNumber()).block();
        if (blockFirmy == null) {
            model.addAttribute("client", new ClientDTO());
            return "clients/add-form";
        }
        ClientDTO clientDTO = clientService.extractClientDTO(blockFirmy);
        model.addAttribute("client", clientDTO);
        return "clients/add-form";
    }

    @GetMapping("/regon")
    public String getByRegon(Model model,
                             @ModelAttribute(name = "nipRegon") @Valid NipRegon nipRegon,
                             BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("client", new ClientDTO());
            return "clients/add-form";
        }
        BlockFirmy blockFirmy = clientClient.getByRegon(nipRegon.getRegonNumber()).block();
        if (blockFirmy == null) {
            model.addAttribute("client", new ClientDTO());
            return "clients/add-form";
        }
        ClientDTO clientDTO = clientService.extractClientDTO(blockFirmy);
        model.addAttribute("client", clientDTO);
        return "clients/add-form";
    }


}
