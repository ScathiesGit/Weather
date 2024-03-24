package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.Location;

public interface LocationRepository {

    void save(Location location);

    void delete(Location location);

}
