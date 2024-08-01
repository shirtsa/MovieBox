package bg.moviebox.web;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegistrationDTO")
    public UserRegistrationDTO userRegistrationDTO() {
       return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserRegistrationDTO userRegistrationDTO
            /* ,@RequestParam("g-recaptcha-response") String reCaptchaResponse */) {

//        boolean isBot = !reCaptchaService
//                .verify(reCaptchaResponse)
//                .map(ReCaptchaResponseDTO::isSuccess)
//                .orElse(false);
//
//        if (isBot) {
//            return "redirect:/";
//        }

        userService.registerUser(userRegistrationDTO);

        return "redirect:/";
    }
}
