package git.scathiesgit.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(exclude = {"timeout", "temp", "description"})
public class Weather implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String lat;

    private String lon;

    private String description;

    private int temp;

    private String country;

    private String icon;

    private int clouds;

    private Integer visibility;

    private String windSpeed;

    @TimeToLive
    private int timeout = 60;

    @JsonProperty("coord")
    private void unwrapCoordinates(Map<String, Object> coordinates) {
        lat = String.valueOf(coordinates.get("lat"));
        lon = String.valueOf(coordinates.get("lon"));
    }

    @JsonProperty("sys")
    private void unwrapSys(Map<String, Object> sys) {
        country = (String) sys.get("country");
    }

    @JsonProperty("weather")
    private void unwrapWeather(List<Map<String, Object>> weather) {
        weather.forEach(map -> {
                    if (map.get("description") != null) {
                        description = (String) map.get("description");
                    }
                    if (map.get("icon") != null) {
                        icon = (String) map.get("icon");
                    }
                });
    }

    @JsonProperty("clouds")
    private void unwrapClouds(Map<String, Object> clouds) {
        this.clouds = (int) clouds.get("all");
    }

    @JsonProperty("wind")
    private void unwrapWind(Map<String, Object> wind) {
        if (wind.get("speed") != null) {
            windSpeed = wind.get("speed").toString();
        }
    }

    @JsonProperty("main")
    private void unwrapMain(Map<String, Object> main) {
        var rawTemp = main.get("temp");
        var className = rawTemp.getClass().getSimpleName();
        if (className.equals("Double")) {
            temp = ((Double) main.get("temp")).intValue();
        } else {
            temp = (Integer) main.get("temp");
        }
    }
}
