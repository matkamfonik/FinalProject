package pl.coderslab.finalproject.controller.viewer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
 
//    private final UserService userService;
//
//    @GetMapping("/create-user")
//    @ResponseBody
//    public String createUser() {
//        User user = new User();
//        user.setUsername("root");
//        user.setPassword("coderslab");
//        userService.saveUser(user);
//        return "admin";
//    }
//
//    @GetMapping("/admin")
//    @ResponseBody
//    public String userInfo(@AuthenticationPrincipal UserDetails customUser) {
//        log.info("customUser class {} " , customUser.getClass());
//        return "You are logged as " + customUser;
//    }
//
//    @GetMapping("/admin")
//    @ResponseBody
//    public String admin(@AuthenticationPrincipal CurrentUser customUser) {
//        User entityUser = customUser.getUser();
//        return "Hello " + entityUser.getUsername();
//    }
}