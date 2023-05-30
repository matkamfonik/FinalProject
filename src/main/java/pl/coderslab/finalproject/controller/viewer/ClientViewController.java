package pl.coderslab.finalproject.controller.viewer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.entities.Client;
import pl.coderslab.finalproject.httpClients.BlockFirmy;
import pl.coderslab.finalproject.httpClients.ClientClient;
import pl.coderslab.finalproject.httpClients.Firma;
import pl.coderslab.finalproject.mappers.ClientMapper;
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
        ClientDTO clientDTO = clientService.get(id);
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
        return "clients/add-form";
    }

    @PostMapping("")
    public String add(@ModelAttribute(name = "client") @Valid ClientDTO clientDTO, BindingResult result, @AuthenticationPrincipal CurrentUser currentUser) {
        if (result.hasErrors()) {
            return "clients/add-form";
        }
        clientService.add(clientDTO, currentUser);
        return "redirect:/view";
    }

    @GetMapping("/nip")
    public String getByNip(Model model, @PathParam("nip") String nip) {
        BlockFirmy blockFirmy = clientClient.getByNip(nip).block();
        if (blockFirmy == null) {
            return "clients/add-form";
        }
        ClientDTO clientDTO = clientService.getClientDTO(blockFirmy);
        model.addAttribute("client", clientDTO);
        return "clients/add-form";
    }

    @GetMapping("/regon")
    public String getByRegon(Model model, @PathParam("regon") String regon) {
        BlockFirmy blockFirmy = clientClient.getByRegon(regon).block();
        if (blockFirmy == null) {
            return "clients/add-form";
        }
        ClientDTO clientDTO = clientService.getClientDTO(blockFirmy);
        model.addAttribute("client", clientDTO);
        return "clients/add-form";
    }


}
