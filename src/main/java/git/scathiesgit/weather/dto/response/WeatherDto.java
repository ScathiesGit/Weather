package git.scathiesgit.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class WeatherDto {

    private String name;

    private String description;

    private int temp;

    private String country;

    private int clouds;

    private String windSpeed;

    private String lat;

    private String lon;

    private String icon;

    private Integer visibility;

}
