package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.Weather;

import java.util.Optional;
import java.util.Set;

public interface WeatherRepository {

    Optional<Weather> findByCoordinates(String lat, String lon);

    Set<Weather> findAllByName(String name);

    void saveByCoordinates(Weather weather);

    void saveByName(String cityName, Set<Weather> weathers);
}
