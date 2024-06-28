package git.scathiesgit.weather.dto.request;

public record SaveLocationDto(Long userId, String name, String lat, String lon) {
}
