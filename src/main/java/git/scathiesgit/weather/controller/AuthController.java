package git.scathiesgit.weather.controller;

import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping
    public String loginForm() {
        return "login";
    }

    @GetMapping("/reg")
    public String registrationForm() {
        return "registration";
    }

    @PostMapping("/reg")
    public String processRegistration(@Valid User user) {
        userService.save(user);
        return "redirect:/auth";
    }
}
