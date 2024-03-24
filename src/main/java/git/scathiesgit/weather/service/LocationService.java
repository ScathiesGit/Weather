package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;

public interface LocationService {

    void save(Location location);

    void delete(Location location, User user);
}
