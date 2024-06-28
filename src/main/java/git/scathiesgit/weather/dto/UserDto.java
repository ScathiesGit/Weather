package git.scathiesgit.weather.dto;

import git.scathiesgit.weather.model.Location;

import java.util.List;

public record UserDto(Long id, String username, List<Location> locations) {
}
