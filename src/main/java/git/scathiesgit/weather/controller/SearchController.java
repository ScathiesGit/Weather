package git.scathiesgit.weather.controller;

import git.scathiesgit.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final WeatherService weatherService;

    @PostMapping("/search")
    public String searchWeather(Model model, String city) {
        model.addAttribute("weathers", weatherService.findByCityName(city));
        return "found-weathers";
    }
}
