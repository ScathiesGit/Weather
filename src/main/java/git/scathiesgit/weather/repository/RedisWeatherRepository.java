package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisWeatherRepository implements WeatherRepository {

    private final RedisTemplate<String, Weather> redisTemplate;

    @Value("${cache.expire}")
    private final int expire;

    @Override
    public Optional<Weather> findByCoordinates(String lat, String lon) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(lat + lon));
    }

    @Override
    public Set<Weather> findAllByName(String name) {
        return redisTemplate.opsForSet().members(name);
    }

    @Override
    public void saveByCoordinates(Weather weather) {
        redisTemplate.opsForValue().set(weather.getLat() + weather.getLon(), weather);
        redisTemplate.expire(weather.getLat() + weather.getLon(), Duration.of(expire, ChronoUnit.SECONDS));
    }

    @Override
    public void saveByName(String cityName, Set<Weather> weathers) {
        redisTemplate.opsForSet().add(cityName, weathers.toArray(new Weather[0]));
        redisTemplate.expire(cityName, Duration.of(expire, ChronoUnit.SECONDS));
    }
}
