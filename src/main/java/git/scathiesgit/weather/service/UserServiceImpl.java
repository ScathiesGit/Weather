package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.model.Weather;
import git.scathiesgit.weather.repository.UserRepository;
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

    @Override
    public User saveUser(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void saveUserLocation(Location location, User user) {
        user.getLocations().add(location);
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
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }

    @Override
    public void deleteUserLocation(Location location, User user) {
        user.getLocations().remove(location);
    }
}
