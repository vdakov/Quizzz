package client.logic;

import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FXMLConfig {

    private final Injector injector;

    /**
     * Constructor for the configuration class
     * @param injector the injector that this configuration will use
     */
    public FXMLConfig(Injector injector) {
        this.injector = injector;
    }

    /**
     * Returns a controller and its parent as a pair
     * @param c the class that the loader refers to
     * @param parts the location of the desired class
     * @param <T> the class type
     * @return a new pair with the control and parent
     */
    public <T> Pair<T, Parent> load(Class<T> c, String... parts) {
        try {
            var loader = new FXMLLoader(getLocation(parts), null, null, new FactoryConfig(), StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            return new Pair<>(ctrl, parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for the location given as parameter and returns the resource found there
     * @param parts the parts of the location
     * @return the resource at the found path
     */
    private URL getLocation(String... parts) {
        var path = Path.of("", parts).toString();
        return FXMLConfig.class.getClassLoader().getResource(path);
    }

    private class FactoryConfig implements BuilderFactory, Callback<Class<?>, Object> {

        /**
         * Creates a new instance of builder with the desired instance of injector
         * @param type the class that there is needed the instance for
         * @return a new builder instance with the desired injector instance
         */
        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return (Builder) () -> injector.getInstance(type);
        }

        /**
         * Returns an object with the current instance of injector in the desired class
         * @param type the class that there is needed the instance for
         * @return the instance of the injector at the desired class
         */
        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
}
