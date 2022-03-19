package kamysh.repository;

import kamysh.entity.LoadStarship;
import kamysh.entity.Starship;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class SessionFactoryBuilder {

    private static final SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        String DATABASE_URL = System.getenv("DATABASE_URL");
        if (DATABASE_URL == null) DATABASE_URL = System.getProperty("DATABASE_URL");
        String DATABASE_USER = System.getenv("DATABASE_USER");
        if (DATABASE_USER == null) DATABASE_USER = System.getProperty("DATABASE_USER");
        String DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD");
        if (DATABASE_PASSWORD == null) DATABASE_PASSWORD = System.getProperty("DATABASE_PASSWORD");

        properties.put(Environment.URL, DATABASE_URL);
        properties.put(Environment.USER, DATABASE_USER);
        properties.put(Environment.PASS, DATABASE_PASSWORD);

        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.put(Environment.FORMAT_SQL, true);
        properties.put(Environment.SHOW_SQL, true);

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Starship.class);
        configuration.addAnnotatedClass(LoadStarship.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
