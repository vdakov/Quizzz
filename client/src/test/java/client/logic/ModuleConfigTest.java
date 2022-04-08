package client.logic;

import com.google.inject.Binder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModuleConfigTest {

    private final ModuleConfig moduleConfig = new ModuleConfig();
    private final

    @Test
    void configure() {
        assertEquals(" ", moduleConfig.configure());
    }
}