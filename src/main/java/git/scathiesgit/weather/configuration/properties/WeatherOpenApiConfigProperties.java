package git.scathiesgit.weather.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather.weather-open-api.url")
@RequiredArgsConstructor
@Getter
public class WeatherOpenApiConfigProperties {

    private final String city;
    private final String coordinates;
}
