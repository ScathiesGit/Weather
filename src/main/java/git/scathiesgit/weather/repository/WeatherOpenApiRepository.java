package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.configuration.properties.WeatherOpenApiConfigProperties;
import git.scathiesgit.weather.model.Weather;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.*;
import static java.util.Optional.*;

@Repository
public class WeatherOpenApiRepository implements WeatherRepository {

    private final RestClient restClient;

    private final String urlByCity;

    private final String urlByCoordinates;

    public WeatherOpenApiRepository(RestClient restClient, WeatherOpenApiConfigProperties props) {
        this.restClient = restClient;
        urlByCity = props.getCity();
        urlByCoordinates = props.getCoordinates();
    }

    @Override
    public Set<Weather> findByCityName(String name) {
        return requireNonNull(find(() -> urlByCity.formatted(name))
                .body(new ParameterizedTypeReference<List<Weather>>() {
                }))
                .stream()
                .map(rawWeather -> findByCoordinates(rawWeather.getLat(), rawWeather.getLon()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(weather -> weather.setName(name))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Weather> findByCoordinates(String lat, String lon) {
        return ofNullable(
                find(() -> urlByCoordinates.formatted(lat, lon)).body(Weather.class)
        );
    }

    private RestClient.ResponseSpec find(Supplier<String> supplierUrl) {
        return restClient
                .get()
                .uri(supplierUrl.get())
                .retrieve();
    }
}
