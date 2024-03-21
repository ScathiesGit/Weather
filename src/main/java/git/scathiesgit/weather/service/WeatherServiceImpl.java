package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.Weather;
import git.scathiesgit.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toSet;
import static org.springframework.web.client.RestClient.ResponseSpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final RestClient restClient;

    @Value("${weather.url.city}")
    private final String urlByCity;

    @Value("${weather.url.coordinates}")
    private final String urlByCoordinates;

    private final WeatherRepository weatherRepo;

    @Override
    public Set<Weather> findByCityName(String cityName) {
        var weathers = weatherRepo.findAllByName(cityName);
        if (!weathers.isEmpty()) {
            return weathers;
        }

        weathers = find(() -> urlByCity.formatted(cityName))
                .body(new ParameterizedTypeReference<List<Weather>>() {
                })
                .stream()
                .map(location -> {
                    var updWeather = findByCoordinates(location.getLat(), location.getLon());
                    updWeather.setName(location.getName());
                    return updWeather;
                })
                .collect(toSet());

        if (weathers.isEmpty()) {
            throw new IllegalArgumentException("Нет данных для локации");
        }

        weatherRepo.saveByName(cityName, weathers);
        return weathers;
    }

    @Override
    public Weather findByCoordinates(String lat, String lon) {
        var weather = weatherRepo.findByCoordinates(lat, lon);
        return weather.isPresent() ? weather.get()
                : find(() -> urlByCoordinates.formatted(lat, lon)).body(Weather.class);
    }

    private ResponseSpec find(Supplier<String> supplierUrl) {
        return restClient
                .get()
                .uri(supplierUrl.get())
                .retrieve();
    }
}
