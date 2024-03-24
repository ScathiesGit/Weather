package git.scathiesgit.weather.service;

import git.scathiesgit.weather.model.Location;
import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepo;

    @Override
    public void save(Location location) {
        locationRepo.save(location);
    }

    @Override
    public void delete(Location location, User user) {
        var index = user.getLocations().indexOf(location);
        locationRepo.delete(user.getLocations().get(index));
    }
}
