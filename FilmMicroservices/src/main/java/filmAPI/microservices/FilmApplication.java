package filmAPI.microservices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Server Main process.
 * Exposes the Resources, making them accessible via the specified path
 */
@ApplicationPath("/api")
public class FilmApplication extends Application {
}
