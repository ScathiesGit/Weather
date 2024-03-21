package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.Weather;

import java.util.Set;

public interface WeatherService {

    Set<Weather> findByCityName(String cityName);

    Weather findByCoordinates(String lat, String lon);
}
