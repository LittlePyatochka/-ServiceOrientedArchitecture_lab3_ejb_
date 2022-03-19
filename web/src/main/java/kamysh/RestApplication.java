package kamysh;

import kamysh.filters.CorsFilter;
import kamysh.controllers.*;
import kamysh.handler.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {
    private final Set<Object> singletons = new HashSet<>();
    private final Set<Class<?>> empty = new HashSet<>();

    public RestApplication() {
        singletons.add(new StarshipResource());
        singletons.add(new NullPointerMapper());
        singletons.add(new StorageServiceRequestErrorMapper());
        singletons.add(new EntryNotFoundMapper());
        singletons.add(new ArgumentFormatExceptionMapper());
        singletons.add(new CorsFilter());
        singletons.add(new SpaceMarineOnBoardMapper());
        singletons.add(new HealthCkeckResource());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
