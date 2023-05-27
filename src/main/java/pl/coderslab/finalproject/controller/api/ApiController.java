package pl.coderslab.finalproject.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.User;
import pl.coderslab.finalproject.services.interfaces.BusinessService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ApiController {
    private final BusinessService businessService;

    @GetMapping("")
    public String home() {
        return "in progress";
    }


}
