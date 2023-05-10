package pl.coderslab.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.finalproject.entities.Person;

@Controller
public class HomeController {
    @GetMapping("/")
    @ResponseBody
    public String home(){
        return "Hello world!!!";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        Person person = new Person();
        person.setFirstName("Marek");
        model.addAttribute(person);
        return "hello";
    }

}
