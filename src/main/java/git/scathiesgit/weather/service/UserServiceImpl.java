package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.model.Weather;
import git.scathiesgit.weather.repository.UserRepository;
import git.scathiesgit.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final WeatherService weatherService;

    private final UserRepository userRepo;

    private final PasswordEncoder passEncoder;

    private final WeatherRepository weatherRepo;

    @Override
    public User saveUser(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public List<Weather> loadUserWeathers(User user) {
        return user.getLocations()
                .stream()
                .map(location -> {
                    var weather = weatherService.findByCoordinates(location.getLat(), location.getLon());
                    weather.setName(location.getName());
                    return weather;
                })
                .toList();
    }

    @Override
    public void saveUserWeather(User user, Weather weather) {
        var location = toLocation(weather);
        userRepo.saveUserLocation(user, location);
        user.addLocation(location);
        weatherRepo.saveByCoordinates(weather);
    }

    @Override
    public void deleteUserWeather(User user, Weather weather) {
        var location = toLocation(weather);
        userRepo.deleteUserLocation(user, location);
        user.getLocations().remove(location);
    }

    @Override
    public User loadUserByUsername(java.lang.String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }

    private Location toLocation(Weather weather) {
        return Location.builder()
                .name(weather.getName())
                .lon(weather.getLon())
                .lat(weather.getLat())
                .build();
    }
}
