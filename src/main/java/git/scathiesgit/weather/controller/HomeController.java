package git.scathiesgit.weather.controller;

import git.scathiesgit.weather.dto.UserDto;
import git.scathiesgit.weather.dto.request.DeleteLocationDto;
import git.scathiesgit.weather.dto.request.SaveLocationDto;
import git.scathiesgit.weather.service.UserService;
import git.scathiesgit.weather.service.WeatherService;
import lombok.AllArgsConstructor;
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

    private final WeatherService weatherService;

    @GetMapping
    public String homePage(@SessionAttribute(required = false) UserDto user, Model model) {
        if (user != null) {
            initModel(model, user);
        }
        return "home";
    }

    @PostMapping
    public String addLocation(@SessionAttribute UserDto user, Model model, SaveLocationDto location) {
        userService.saveLocation(user, location);
        initModel(model, user);
        return "home";
    }

    @PostMapping("/delete")
    public String deleteLocation(@SessionAttribute UserDto user, Model model, DeleteLocationDto location) {
        userService.deleteLocation(user, location);
        initModel(model, user);
        return "home";
    }

    @GetMapping("/search")
    public String searchWeather(Model model, String city) {
        model.addAttribute("weathers", weatherService.fetch(city));
        return "found-weathers";
    }

    private void initModel(Model model, UserDto user) {
        model.addAttribute("weathers", userService.loadWeather(user));
    }
}
