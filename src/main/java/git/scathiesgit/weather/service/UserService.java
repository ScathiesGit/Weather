package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.model.Weather;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User saveUser(User user);

    void saveUserLocation(Location location, User user);

    void deleteUserLocation(Location location, User user);

    List<Weather> loadUserWeathers(User user);
}
