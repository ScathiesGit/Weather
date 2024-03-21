package git.scathiesgit.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(exclude = {"timeout", "temp", "description"})
public class Weather implements Serializable {

    private String name;

    private String lat;

    private String lon;

    private String description;

    private Integer temp;

    private String country;

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
        description = (String) weather.get(0).get("description");
    }

    @JsonProperty("main")
    private void unwrapMain(Map<String, Object> main) {
        temp = ((Double) main.get("temp")).intValue();
    }
}
