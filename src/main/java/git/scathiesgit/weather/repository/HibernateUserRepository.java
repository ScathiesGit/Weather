package git.scathiesgit.weather.repository;

import git.scathiesgit.weather.model.User;
import git.scathiesgit.weather.repository.exception.UserAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private final HibernateExecutor executor;

    @Override
    public User save(User user) {
        try {
            executor.execute(session -> session.persist(user));
        } catch (Exception e) {
            throw new UserAlreadyExistsException(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return ofNullable(
                executor.executeQuery(session -> {
                    User user = session.createQuery("from User u where u.username = :username", User.class)
                            .setParameter("username", username)
                            .uniqueResult();
                    Hibernate.initialize(user.getLocations());
                    return user;
                }));
    }
}
