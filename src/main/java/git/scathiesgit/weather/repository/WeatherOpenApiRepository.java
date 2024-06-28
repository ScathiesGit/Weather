package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.configuration.properties.WeatherOpenApiConfigProperties;
import git.scathiesgit.weather.model.Weather;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.function.Supplier;

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
        var weathers = new HashSet<>(Objects.requireNonNull(find(() -> urlByCity.formatted(name))
                .body(new ParameterizedTypeReference<List<Weather>>() {
                })));

//                .stream()
//                .map(location -> {
//                    var updWeather = findByCoordinates(location.getLat(), location.getLon());
//                    updWeather.setName(name);
//                    return updWeather;
//                })
//                .collect(toSet());

        if (weathers.isEmpty()) {
            throw new IllegalArgumentException("Нет данных для локации");
        }

        return weathers;
    }

    @Override
    public Optional<Weather> findByCoordinates(String lat, String lon) {
        return Optional.ofNullable(
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
