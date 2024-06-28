package git.scathiesgit.weather.service;

import git.scathiesgit.weather.dto.UserDto;
import git.scathiesgit.weather.dto.request.DeleteLocationDto;
import git.scathiesgit.weather.dto.request.SaveLocationDto;
import git.scathiesgit.weather.dto.response.WeatherDto;
import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.repository.LocationRepository;
import git.scathiesgit.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final LocationRepository locationRepository;

    private final PasswordEncoder passEncoder;

    private final WeatherServiceImpl weatherService;

    @Override
    public void save(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public List<WeatherDto> loadWeather(UserDto user) {
        return user.locations().stream()
                .map(location -> weatherService.fetch(location.getLat(), location.getLon()))
                .toList();
    }

    @Override
    public void saveLocation(UserDto user, SaveLocationDto location) {
        locationRepository.save(
                Location.builder()
                        .userId(user.id())
                        .lat(location.lat())
                        .lon(location.lon())
                        .build()
        );
    }

    @Override
    public void deleteLocation(UserDto user, DeleteLocationDto location) {
        locationRepository.delete(
                Location.builder()
                        .userId(user.id())
                        .lat(location.lat())
                        .lon(location.lon())
                        .build()
        );
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }
}
