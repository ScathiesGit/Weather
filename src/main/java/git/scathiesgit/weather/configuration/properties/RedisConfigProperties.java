package git.scathiesgit.weather.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
@RequiredArgsConstructor
@Getter
public class RedisConfigProperties {

    private final String host;

    private final Integer port;
}
