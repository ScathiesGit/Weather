package git.scathiesgit.weather.configuration;

import git.scathiesgit.weather.configuration.properties.RedisConfigProperties;
import git.scathiesgit.weather.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisConfigProperties redisProps;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProps.getHost(), redisProps.getPort());
    }

    @Bean
    public RedisTemplate<String, Weather> redisTemplate() {
        final RedisTemplate<String, Weather> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
