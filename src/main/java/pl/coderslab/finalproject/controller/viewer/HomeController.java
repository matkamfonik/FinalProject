package pl.coderslab.finalproject.controller.viewer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.User;
import pl.coderslab.finalproject.services.interfaces.BusinessService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BusinessService businessService;
    @GetMapping("/")
    public String home(Model model,@AuthenticationPrincipal CurrentUser currentUser){
        User user = currentUser.getUser();
        model.addAttribute("user", user);

        List<Business> businesses = businessService.findAllBusinessNameByUserId(user.getId());
        model.addAttribute("businesses", businesses);
        return "home";
    }


}
