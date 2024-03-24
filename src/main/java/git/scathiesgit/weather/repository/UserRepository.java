package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(java.lang.String username);
}
