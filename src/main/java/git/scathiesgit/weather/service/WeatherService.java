package git.scathiesgit.weather.service;

import git.scathiesgit.weather.dto.response.WeatherDto;

import java.util.List;

public interface WeatherService {

    List<WeatherDto> fetch(String cityName);

    WeatherDto fetch(String latitude, String longitude);

}
