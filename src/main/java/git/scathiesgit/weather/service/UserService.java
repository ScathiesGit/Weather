package git.scathiesgit.weather.service;

import git.scathiesgit.weather.dto.UserDto;
import git.scathiesgit.weather.dto.request.DeleteLocationDto;
import git.scathiesgit.weather.dto.request.SaveLocationDto;
import git.scathiesgit.weather.dto.response.WeatherDto;
import git.scathiesgit.weather.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void save(User user);

    List<WeatherDto> loadWeather(UserDto user);

    void saveLocation(UserDto user, SaveLocationDto location);

    void deleteLocation(UserDto user, DeleteLocationDto location);

}
