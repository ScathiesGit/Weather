package git.scathiesgit.weather.controller;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.service.LocationService;
import git.scathiesgit.weather.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {

    private UserService userService;

    private LocationService locationService;

    @GetMapping
    public String homePage(/*@SessionAttribute(required = false) User user,*/
            @AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            loadUserWeatherToModel(model, user);
        }
        return "home";
    }

    @PostMapping
    public String addLocation(@SessionAttribute User user, Model model, Location location) {
        locationService.save(location);
        userService.saveUserLocation(location, user);
        loadUserWeatherToModel(model, user);
        return "home";
    }

    @PostMapping("/delete")
    public String deleteLocation(@SessionAttribute User user, Model model, Location location) {
        locationService.delete(location, user);
        userService.deleteUserLocation(location, user);
        loadUserWeatherToModel(model, user);
        return "home";
    }

    private void loadUserWeatherToModel(Model model, User user) {
        model.addAttribute("weathers", userService.loadUserWeathers(user));
    }
}
