package git.scathiesgit.weather.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class HibernateExecutor {

    private final SessionFactory entityManagerFactory;

    public void execute(Consumer<Session> consumer) {
        try (var session = entityManagerFactory.openSession()) {
            session.beginTransaction();

            consumer.accept(session);

            session.getTransaction().commit();
        }
    }

    public <T> T executeQuery(Function<Session, T> function) {
        T result;
        try (var session = entityManagerFactory.openSession()) {
            session.beginTransaction();

            result = function.apply(session);

            session.getTransaction().commit();
        }
        return result;
    }

}
