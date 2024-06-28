package git.scathiesgit.weather.service;

import git.scathiesgit.weather.dto.response.WeatherDto;
import git.scathiesgit.weather.model.Weather;
import git.scathiesgit.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepo;

    @Override
    public List<WeatherDto> fetch(String name) {
        return weatherRepo.findByCityName(name)
                .stream()
                .map(this::toWeatherDto)
                .toList();
    }

    @Override
    public WeatherDto fetch(String latitude, String longitude) {
        return weatherRepo.findByCoordinates(latitude, longitude)
                .map(this::toWeatherDto)
                .orElseThrow(() -> new RuntimeException("Weather not found for the given coordinates"));
    }

    private WeatherDto toWeatherDto(Weather weather) {
        return null;
    }
}
