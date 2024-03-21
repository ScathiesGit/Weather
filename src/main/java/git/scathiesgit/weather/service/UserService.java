package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.model.Weather;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User saveUser(User user);

    List<Weather> loadUserWeathers(User user);

    void saveUserWeather(User user, Weather weather);

    void deleteUserWeather(User user, Weather weather);
}
